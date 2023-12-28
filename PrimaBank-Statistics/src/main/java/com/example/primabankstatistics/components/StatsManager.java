package com.example.primabankstatistics.components;

import com.example.primabankstatistics.model.Statistic;
import com.example.primabankstatistics.repositories.StatsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class StatsManager {

    @Autowired
    private StatsRepository statsRepository;

    public Statistic getLatestCountryStatistic() {
        // Query the database to retrieve the latest statistic with the category "Country"
        return statsRepository.findTopByCategoryOrderByIdDesc("country");
    }

    public Statistic getLatestTypeStatistic() {
        // Query the database to retrieve the latest statistic with the category "Type"
        return statsRepository.findTopByCategoryOrderByIdDesc("type");
    }

    public Statistic getLatestOrigineStatistic() {
        // Query the database to retrieve the latest statistic with the category "Origine"
        return statsRepository.findTopByCategoryOrderByIdDesc("origine");
    }

    public Statistic getLatestCountryPrice() {
        // Query the database to retrieve the latest statistic with the category "TotalPrice"
        return statsRepository.findTopByCategoryOrderByIdDesc("countryPrice");
    }


    

}
