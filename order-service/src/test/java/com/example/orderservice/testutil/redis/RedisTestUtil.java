package com.example.orderservice.testutil.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTestUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cleanAll() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
    }
}
