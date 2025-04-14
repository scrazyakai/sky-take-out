package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        log.info("开始创建redis模块对象...");
        //设置redis连接工厂对象
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis key的序列化器
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
