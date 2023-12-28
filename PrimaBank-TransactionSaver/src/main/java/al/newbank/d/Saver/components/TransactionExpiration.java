package al.newbank.d.Saver.components;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import al.newbank.d.Saver.entities.Transaction;
import al.newbank.d.Saver.repositories.TransactionRepository;

@Component
public class TransactionExpiration {

    @Autowired
    TransactionRepository transactionRepository;


    public void deleteTransactionsOlderThan(LocalDateTime date) {
        
        LocalDateTime currentDate = LocalDateTime.now();

        // Calcul de la date 3 mois avant la date actuelle
        LocalDateTime threeMonthsAgo = currentDate.minus(3, ChronoUnit.MONTHS);

        // Récupérer les transactions avec un timestamp plus ancien que trois mois
        List<Transaction> oldTransactions = transactionRepository.findByCreatedAtBefore(threeMonthsAgo);

        // Supprimer les transactions récupérées
        transactionRepository.deleteAll(oldTransactions);
    }

    
    
}
