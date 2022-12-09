package com.iduck;

import com.iduck.security.jwt.util.IJwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/3
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class IJwtUtilsTest {

    @Test
    public void createTokenTest() {
        String userId = "syb-duck";
        System.out.println("需要加密的userId:" + userId + "; 配置文件中密钥:jwt-secret-iduck");
        String token = IJwtUtils.createToken(userId);
        System.out.println("token获取:" + token);
        System.out.println("token校验合法性:" + IJwtUtils.verifyToken(token));

        System.out.println("--------------------------------------------------------------");

        System.out.println("需要加密的userId:" + userId + "; 自定义:hello-world");
        String token1 = IJwtUtils.createToken(userId, "hello-world", 1000);
        System.out.println("token获取:" + token1);
        System.out.println("token校验合法性:" + IJwtUtils.verifyToken(token1, "hello-world"));
    }
}
