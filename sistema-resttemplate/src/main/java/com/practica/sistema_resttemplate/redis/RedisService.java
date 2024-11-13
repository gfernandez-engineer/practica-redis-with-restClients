package com.practica.sistema_resttemplate.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void saveInRedis(String key, String value, int exp){
        stringRedisTemplate.opsForValue().set(key,value);
        stringRedisTemplate.expire(key,exp, TimeUnit.MINUTES);
    }

    public String getDataFromRedis(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleDataRedis(String key){
        stringRedisTemplate.delete(key);
    }
}
