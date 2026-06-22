package com.github.zhygtx.napcat;

import com.github.zhygtx.napcat.testapp.TestNapCatApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.WebSocketHandler;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * NapCat SDK 集成测试
 *
 * 使用 @SpringBootTest 启动完整的 Spring 上下文，验证：
 *   1. SDK 的自动配置能被正确识别和加载
 *   2. WebSocketHandler Bean 能被正确注入
 *   3. 整个应用上下文能正常启动（意味着 WebSocket 端点也注册成功）
 *
 * 运行方式:
 *   - contextLoads() : 快速验证，启动后立刻退出，检查 SDK 是否正常
 *   - runWithRealNapCat() : 启动后保持运行 10 分钟，
 *     期间你可以让真实的 NapCat 连接到 ws://localhost:8080/ws/bot/[botQQ]/[secretKey]
 */
@SpringBootTest(classes = TestNapCatApplication.class)
class NapCatSdkIntegrationTest {

    @Autowired
    private WebSocketHandler webSocketHandler;

    /**
     * 快速验证：Spring 上下文能正常加载，且 WebSocketHandler 已被注册。
     */
    @Test
    void contextLoads() {
        assertNotNull(webSocketHandler, "WebSocketHandler 应该被 SDK 自动配置注入");
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  OK - SDK 自动配置生效");
        System.out.println("  OK - WebSocketHandler 已注册: " + webSocketHandler.getClass().getSimpleName());
        System.out.println("  OK - 上下文加载测试通过");
        System.out.println("==================================================");
        System.out.println();
    }

    /**
     * 联调测试：启动 Spring 应用并保持运行 10 分钟。
     *
     * 运行此测试期间，你可以让真实的 NapCat 连接到：
     *   ws://localhost:8080/ws/bot/你的BotQQ/任意密钥
     *
     * 连接成功后你应该能在控制台看到：
     *   "========== NapCat 连接成功 =========="
     *   "[收到消息] session=xxx, payload=..."
     *
     * 注意：如果你使用的是远程服务器上的 NapCat，请把 localhost 换成你的机器 IP，
     * 并且确保 8080 端口没有被防火墙阻挡。
     */
    @Test
    void runWithRealNapCat() throws InterruptedException {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  NapCat SDK 测试服务已启动!");
        System.out.println("  监听端口  : 8080");
        System.out.println("  WebSocket : ws://localhost:8080/ws/bot/[botQQ]/[secretKey]");
        System.out.println();
        System.out.println("  测试步骤:");
        System.out.println("    1. 打开 NapCat 的配置文件 / 管理界面");
        System.out.println("    2. 把反向 WebSocket 地址改为: ws://你的本机IP:8080/ws/bot/123456789/testKey");
        System.out.println("    3. 让 NapCat 连接（可能需要重启 NapCat）");
        System.out.println("    4. 在 NapCat 中发一条群消息 / 私聊");
        System.out.println("    5. 回到这个控制台，你应该能看到连接日志和消息 JSON");
        System.out.println();
        System.out.println("  本测试将在 10 分钟后自动结束");
        System.out.println("  也可以手动点击 IDEA 的停止按钮");
        System.out.println("==================================================");
        System.out.println();

        Thread.sleep(10 * 60 * 1000);
    }
}
