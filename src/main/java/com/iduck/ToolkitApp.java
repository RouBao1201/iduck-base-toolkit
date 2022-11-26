package com.iduck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 工具服务
 *
 * @author songYanBin
 * @since 2022/11/24
 */
@SpringBootApplication
public class ToolkitApp {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ToolkitApp.class);
        app.run(args);
    }
}
