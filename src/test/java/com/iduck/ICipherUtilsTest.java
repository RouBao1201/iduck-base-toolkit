package com.iduck;

import com.iduck.security.cipher.util.ICipherUtils;
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
public class ICipherUtilsTest {

    @Test
    public void md5Test() {
        String str = "123syb+qqt";
        System.out.println("加密前:" + str);

        System.out.println("-------------------------------------------------------");
        System.out.println("配置文件配置盐值salt[cipher.config.md5.salt=iduck-md5-salt]");
        String encrypt = ICipherUtils.MD5().encrypt(str);
        System.out.println("加密后:" + encrypt);
        System.out.println("（输入正确密码）验证密码是否正确:" + ICipherUtils.MD5().verify(encrypt, str));
        System.out.println("（输入错误密码）验证密码是否正确:" + ICipherUtils.MD5().verify(encrypt, "123"));

        System.out.println("-------------------------------------------------------");
        System.out.println("自定义盐值[hello-world]");
        String encrypt1 = ICipherUtils.MD5().encrypt(str, "hello-world");
        System.out.println("加密后:" + encrypt1);
        System.out.println("（输入正确密码和盐值）验证密码是否正确:" + ICipherUtils.MD5().verify(encrypt1, str, "hello-world"));
        System.out.println("（输入错误密码和正确盐值）验证密码是否正确:" + ICipherUtils.MD5().verify(encrypt1, "123", "hello-world"));
        System.out.println("（输入正确密码和错误盐值）验证密码是否正确:" + ICipherUtils.MD5().verify(encrypt1, str, "hello"));
    }
}
