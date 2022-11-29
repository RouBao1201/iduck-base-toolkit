package com.iduck;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 工具服务
 *
 * @author songYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
@SpringBootApplication
public class ToolkitApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ToolkitApp.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
