package com.example.primabankstatistics.redis;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("Statistics")
@Getter
@Setter
@ToString
public class Stat implements Serializable {

    @Id
    private String id;
    @Indexed 
    private String name;
    @Indexed 
    private String value;

    public Stat() {
    }

    public Stat(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
