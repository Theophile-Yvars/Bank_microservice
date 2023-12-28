package com.example.primabankstatistics.components;

import com.example.primabankstatistics.dto.TransactionDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component @Transactional
public class TransactionsService {

    public String transactionUrl = "http://service-transaction-backend:8080/transaction/getAll";

    public List<TransactionDTO> getAllTransactions() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransactionDTO[]> response = restTemplate.getForEntity(transactionUrl, TransactionDTO[].class);
        TransactionDTO[] transactions = response.getBody();
        return Arrays.asList(transactions);
    }

}
