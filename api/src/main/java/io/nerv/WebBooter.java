package io.nerv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 应用启动入口，同时扫描应用业务包与 EVA 组件。
 *
 * @author PKAQ
 * @date 2026-11-02
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = {"io.nerv", "org.pkaq"})
public class WebBooter {
    /**
     * 启动应用；字典初始化由 EVA 自身组件管理。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(WebBooter.class, args);
    }
}
