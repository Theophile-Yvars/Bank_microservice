package al.newbank.d.PrimaBankTransaction.components;

import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;
import al.newbank.d.PrimaBankTransaction.entities.Transaction;
import al.newbank.d.PrimaBankTransaction.interfaces.IGetTransactions;
import al.newbank.d.PrimaBankTransaction.interfaces.ISaveTransaction;
import al.newbank.d.PrimaBankTransaction.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@Transactional
public class BankTransaction implements IGetTransactions, ISaveTransaction {
    Logger logger = LoggerFactory.getLogger(BankTransaction.class);

    private TransactionRepository transactionRepository;

    @Autowired
    public BankTransaction(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean save(TransactionDTO transactionDTO){
        logger.info("SAVE TRANSACTION");

        Transaction transaction = this.transactionRepository.save(
                new Transaction(
                        transactionDTO.getName(),
                        transactionDTO.getClient(),
                        transactionDTO.getPrice(),
                        transactionDTO.getCountry(),
                        transactionDTO.getType(),
                        transactionDTO.getOrigine(),
                        transactionDTO.getTargetClient()
                )
        );

        logger.info(transaction.toString());

        return true;
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactionEntitiesList = transactionRepository.findAll();
        List<TransactionDTO> transactionDtoList = transactionEntitiesList.stream()
                .map(entity -> new TransactionDTO(
                        entity.getName(),
                        entity.getClient(),
                        entity.getPrice(),
                        entity.getCountry(),
                        entity.getType(),
                        entity.getOrigine(),
                        entity.getTagetClient()
                ))
                .collect(Collectors.toList());

        return transactionDtoList;
    }
}