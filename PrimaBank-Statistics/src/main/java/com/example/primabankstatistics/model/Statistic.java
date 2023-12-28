package com.example.primabankstatistics.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Table(name = "statistic")
@Entity(name = "statistic")

public class Statistic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // You can use a unique identifier for each statistic entry, e.g., a generated ID.

    private String name; // Name of the statistic (e.g., "Average Price", "Transactions by Country", etc.)
    private String category; // Category of the statistic (e.g., "Price", "Country", "Type", "Origine", etc.)
    private String value; // The actual value of the statistic (e.g., a numerical value or a JSON representation of data).

    // Constructors, getters, and setters as needed.

    public Statistic() {
    }

    public Statistic(String name, String category, String value) {
        this.name = name;
        this.category = category;
        this.value = value;
    }

    // Getters and setters for the fields.

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setValue(String value) {
        this.value = value;
    }
}