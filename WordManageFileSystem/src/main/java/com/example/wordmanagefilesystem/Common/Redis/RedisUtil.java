package com.example.wordmanagefilesystem.Common.Redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String , Object> redisTemplate;

    //添加缓存
    public void put(String key , Object value , long timeOut){
        redisTemplate.opsForValue().set(key , value , timeOut , TimeUnit.SECONDS);
    }

    //添加缓存（永久缓存）
    public void putValueForever(String key , Object value){
        redisTemplate.opsForValue().set(key , value);
    }

    //得到缓存
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    //获得所有缓存set集合
    public Set<String> getKeys(String pattern){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate.keys(pattern);
    }

    //删除所有缓存
    public void deleteAllRedis(Set<String> keys){
        redisTemplate.delete(keys);
    }

    //删除缓存
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
