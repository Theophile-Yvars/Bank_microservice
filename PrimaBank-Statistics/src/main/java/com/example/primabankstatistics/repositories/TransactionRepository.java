package com.example.primabankstatistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.primabankstatistics.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
   

}
