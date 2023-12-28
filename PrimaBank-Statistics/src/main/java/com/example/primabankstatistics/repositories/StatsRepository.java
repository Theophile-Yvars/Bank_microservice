package com.example.primabankstatistics.repositories;

import com.example.primabankstatistics.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Statistic, Long> {
    Statistic findTopByCategoryOrderByIdDesc(String country);

}
