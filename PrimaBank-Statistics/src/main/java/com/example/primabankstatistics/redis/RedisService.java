package com.example.primabankstatistics.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
    void setValue(String key, String value);

    Object getValue(String key);

    void deleteKey(String key);

    public Set<String> getAllKeys();
    public Map<String,String> getAllKeysWithValues();

    // get all keys

    // get all values

}