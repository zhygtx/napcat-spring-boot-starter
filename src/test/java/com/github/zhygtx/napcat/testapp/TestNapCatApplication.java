package com.github.zhygtx.napcat.testapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 模拟主项目的启动类。
 *
 * 这个类模拟一个真实的 Spring Boot 应用（比如你的 demo 项目），它通过引入
 * napcat-spring-boot-starter 依赖来获得 NapCat WebSocket 接入能力。
 *
 * 运行方式：直接在 IDEA 中右键 -> Run 'TestNapCatApplication'，
 * 或者在命令行执行：mvn spring-boot:run
 *
 * 启动后让 NapCat 反向连接到：
 *   ws://localhost:8080/ws/bot/你的BotQQ/任意密钥
 *
 * 停止服务：在 IDEA 运行面板点击停止按钮，或按 Ctrl+C。
 */
@SpringBootApplication
public class TestNapCatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNapCatApplication.class, args);
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  NapCat SDK 测试服务已启动!");
        System.out.println("  监听端口  : 8080");
        System.out.println("  WebSocket : ws://localhost:8080/ws/bot/[botQQ]/[secretKey]");
        System.out.println("  测试连接  : 让 NapCat 反向连接到上面的地址");
        System.out.println("  停止服务  : Ctrl+C 或 IDEA 停止按钮");
        System.out.println("==================================================");
        System.out.println();
    }
}
