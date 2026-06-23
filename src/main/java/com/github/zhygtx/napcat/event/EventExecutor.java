package com.github.zhygtx.napcat.event;

import com.github.zhygtx.napcat.config.NapCatProperties;
import jakarta.annotation.PreDestroy;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事件线程池执行器。
 * <p>
 * 管理所有事件处理的异步执行，将事件处理从 WebSocket I/O 线程中剥离，
 * 避免 I/O 线程因用户 Handler 中的耗时操作（DB、HTTP 等）而被阻塞。
 * <p>
 * 使用 {@link ThreadPoolExecutor.CallerRunsPolicy} 作为拒绝策略：
 * 当队列满时，任务会在调用方线程（WebSocket I/O 线程）中同步执行，
 * 此机制是天然的背压保护——NapCat 端会感受到反压。
 * <p>
 * 配置来源于 {@code napcat.task-pool}。
 */
@Component
public class EventExecutor {

    private static final Logger log = LoggerFactory.getLogger(EventExecutor.class);

    private final ThreadPoolExecutor executor;

    public EventExecutor(NapCatProperties properties) {
        NapCatProperties.TaskPool config = properties.getTaskPool();
        int core = config.getCorePoolSize();
        int max = config.getMaxPoolSize();
        int keepAlive = config.getKeepAliveTime();
        int queueCapacity = config.getQueueCapacity();
        String prefix = config.getThreadNamePrefix();

        this.executor = new ThreadPoolExecutor(
                core,
                max,
                keepAlive,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                new NamedThreadFactory(prefix),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        log.info("EventExecutor 初始化完成, core={}, max={}, queue={}, keepAlive={}s, prefix={}",
                core, max, queueCapacity, keepAlive, prefix);
    }

    /**
     * 提交一个事件处理任务到线程池。
     * <p>
     * 队列满时会回退到调用方线程（CallerRunsPolicy），防止无限制堆积。
     *
     * @param task 要执行的任务
     */
    public void submit(Runnable task) {
        executor.submit(task);
    }

    /**
     * 优雅关闭线程池。
     * <p>
     * 等待已提交的任务完成，最多等待 5 秒。
     */
    @PreDestroy
    public void shutdown() {
        log.info("正在关闭 EventExecutor...");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("EventExecutor 在 5 秒内未完全停止，强制关闭");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("EventExecutor 已关闭");
    }

    /**
     * 带名称的线程工厂，用于指定线程名前缀。
     */
    private static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger counter = new AtomicInteger(0);
        private final String namePrefix;

        NamedThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r);
            t.setName(namePrefix + counter.incrementAndGet());
            t.setDaemon(false);
            return t;
        }
    }
}
