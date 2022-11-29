package com.iduck.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * redis配置类
 *
 * @author SongYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
@Configuration
public class RedisConfig {
    /**
     * 序列化配置
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return RedisTemplate<String, Object>
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        FastJson2JsonRedisSerializer<Object> fastJsonSerializer = new FastJson2JsonRedisSerializer<>(Object.class);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(fastJsonSerializer);

        redisTemplate.setHashKeySerializer(fastJsonSerializer);
        redisTemplate.setHashValueSerializer(fastJsonSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}