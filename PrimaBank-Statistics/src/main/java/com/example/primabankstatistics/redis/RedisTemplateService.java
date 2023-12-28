package com.example.primabankstatistics.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTemplateService implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisTemplateService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }

    public Map<String, String> getAllKeysWithValues() {
        Set<String> keys = redisTemplate.keys("*");
        Map<String, String> keyValueMap = new HashMap<>();

        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);
            keyValueMap.put(key, value);
        }

        return keyValueMap;
}


}
