package com.iduck;

import com.iduck.redis.util.IRedisHelper;
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
public class IRedisHelperTest {

    @Test
    public void setTest() {
        System.out.println("设置redis：[key-value:syb-songyanbin]");
        IRedisHelper.set("syb", "songyanbin");
    }
}
