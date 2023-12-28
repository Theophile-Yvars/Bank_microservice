package com.example.primabankstatistics.controllers;

import com.example.primabankstatistics.components.StatsCalculator;
import com.example.primabankstatistics.components.StatsManager;
import com.example.primabankstatistics.model.Statistic;
import com.example.primabankstatistics.redis.RedisService;
import com.example.primabankstatistics.redis.Stat;
import com.example.primabankstatistics.repositories.TransactionRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/stats")
public class StatsController {
    
    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private StatsCalculator statsCalculator;

    @Autowired
    private StatsManager statsManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RedisService redisService;

    @GetMapping("/start-calculations")
    public ResponseEntity<String>  startCalculations(){

        logger.info("");
        logger.info("==================================================================");
        logger.info("");

        statsCalculator.calculateTransactionsByCountry();
        logger.info("\n" + "TRANSACTIONS BY COUNTRY CALCULATED");
        statsCalculator.calculateTransactionsByType();
        logger.info("\n" +"TRANSACTIONS BY TYPE CALCULATED");
        statsCalculator.calculateTransactionsByOrigine();
        logger.info("\n" +"TRANSACTIONS BY ORIGINE CALCULATED");
        statsCalculator.calculateTotalPriceByCountry();
        logger.info("\n" +"TOTAL PRICE BY COUNTRY CALCULATED");

        return ResponseEntity.ok("ALL CALCULATIONS DONE SUCCESSFULLY");
    }

    @GetMapping("/get-last-stats")
    public List<Statistic> getLastStats(){
        logger.info("");
        logger.info("==================================================================");
        logger.info("");

        List<Statistic> stats = new ArrayList<>();
        stats.add(statsManager.getLatestCountryStatistic());
        stats.add(statsManager.getLatestTypeStatistic());
        stats.add(statsManager.getLatestOrigineStatistic());
        stats.add(statsManager.getLatestCountryPrice());
        logger.info("\n" + "GET_LAST_STATS : " + stats.toString());
        return stats;
    }

    @GetMapping("/fetch-redis")
    public Map<String,String> fetchStats(){
       this.redisService.getAllKeys().forEach(key -> {
           logger.info("\n" + "KEY : " + key + " VALUE : " + this.redisService.getValue(key));
       });
       return this.redisService.getAllKeysWithValues();

    
    }
}
