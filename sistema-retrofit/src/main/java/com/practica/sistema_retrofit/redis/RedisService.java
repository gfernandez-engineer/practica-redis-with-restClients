package com.practica.sistema_retrofit.redis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor /* Genera un constructor con todos los campos definidos como FINAL.
Permite la inyección con constructor. Se puede usar esto en lugar del constructor manual*/

//@NoArgsConstructor // Genera un constructor sin argumentos, osea vacio
//@AllArgsConstructor // Genera un constructor que incluye absolutamente todos los campos de la clase.
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    /*public RedisService(StringRedisTemplate redisTemplate) {
        this.stringRedisTemplate = redisTemplate;
    }*/

    public void saveInRedis(String key, String value, int exp){
        stringRedisTemplate.opsForValue().set(key,value);
        stringRedisTemplate.expire(key,exp, TimeUnit.MINUTES);
    }

    public String getDataFromRedis(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteDataRedis(String key){
        stringRedisTemplate.delete(key);
    }
}
