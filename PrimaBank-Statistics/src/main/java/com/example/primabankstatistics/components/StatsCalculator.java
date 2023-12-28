package com.example.primabankstatistics.components;

import com.example.primabankstatistics.dto.TransactionDTO;
import com.example.primabankstatistics.model.Statistic;
import com.example.primabankstatistics.model.Transaction;
import com.example.primabankstatistics.redis.RedisService;
import com.example.primabankstatistics.redis.Stat;
import com.example.primabankstatistics.repositories.StatsRepository;
import com.example.primabankstatistics.repositories.TransactionRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class StatsCalculator {

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RedisService redisService;


    public List<Transaction> getAllTransactions(){
        return this.transactionRepository.findAll();
    }


    public float getAveragePrice() {
        float sum = 0;
        int count = 0;
        for (TransactionDTO transaction : transactionsService.getAllTransactions()) {
            sum += transaction.getPrice();
            count++;
        }
        return sum / count;
    }

    public Map<String, Integer> calculateTransactionsByCountry() {
        Map<String, Integer> transactionsByCountry = new HashMap<>();
        List<Transaction> transactions = this.getAllTransactions();
        for (Transaction transaction : transactions) {
            String country = transaction.getCountry();

            // Check if the country is not null (you may want to validate data)
            if (country != null) {
                // Increment the count for the country
                transactionsByCountry.put(country, transactionsByCountry.getOrDefault(country, 0) + 1);
            }
        }
        statsRepository.save(new Statistic("transactionsByCountry", "country", transactionsByCountry.toString()));
        return transactionsByCountry;
    }

    public Map<String,Integer> calculateTransactionsByType(){
        Map<String,Integer> transactionsByType = new HashMap<>();
        List<Transaction> transactions = this.getAllTransactions();
        for (Transaction transaction : transactions) {
            String type = transaction.getType();
            if(type != null){
                transactionsByType.put(type, transactionsByType.getOrDefault(type, 0) + 1);
            }
        }
        statsRepository.save(new Statistic("transactionsByType", "type", transactionsByType.toString()));
        return transactionsByType;
    }

    public Map<String,Integer> calculateTransactionsByOrigine(){
        Map<String,Integer> transactionsByOrigine = new HashMap<>();
        List<Transaction> transactions = this.getAllTransactions();
        for (Transaction transaction : transactions) {
            String origine = transaction.getOrigine();
            if(origine != null){
                transactionsByOrigine.put(origine, transactionsByOrigine.getOrDefault(origine, 0) + 1);
            }
        }
        statsRepository.save(new Statistic("transactionsByOrigine", "origine", transactionsByOrigine.toString()));
        return transactionsByOrigine;
    }

    public Map<String,Float> calculateTotalPriceByCountry(){
        Map<String,Float> totalPriceByCountry = new HashMap<>();
        List<Transaction> transactions = this.getAllTransactions();
        for (Transaction transaction : transactions) {
            String country = transaction.getCountry();
            if(country != null){
                totalPriceByCountry.put(country, (float) (totalPriceByCountry.getOrDefault(country, 0f) + transaction.getPrice()));
            }
        }
        statsRepository.save(new Statistic("totalPriceByCountry", "countryPrice", totalPriceByCountry.toString()));
        return totalPriceByCountry;
    }

    public void updateInternetstats(TransactionDTO transactionDTO){
        String origine = transactionDTO.getOrigine();
        if(origine != null){
            this.incrementStats(origine);
        }

    }

    public void updateCountryStats(TransactionDTO transactionDTO){
        String country = transactionDTO.getCountry();
        String key = "country-"+country;
        if(country != null){
            this.incrementStats(key);
        }
    }

    public void updateTypeStats(TransactionDTO transactionDTO){
        String type = transactionDTO.getType();
        if(type != null){
            this.incrementStats(type);
        }
    }

    

    public void incrementStats(String key){
        if(redisService.getValue(key) == null){
            redisService.setValue(key, "1");

        }
        else{
            String internet = redisService.getValue(key).toString();
            int internetInt = Integer.parseInt(internet);
            internetInt++;
            redisService.setValue(key, String.valueOf(internetInt));
        }
    }

 


  



}
