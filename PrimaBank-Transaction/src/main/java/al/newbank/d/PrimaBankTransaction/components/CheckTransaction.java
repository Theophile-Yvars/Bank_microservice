package al.newbank.d.PrimaBankTransaction.components;

import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;
import al.newbank.d.PrimaBankTransaction.interfaces.IGetTransactions;
import al.newbank.d.PrimaBankTransaction.interfaces.IObserveTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckTransaction implements IObserveTransaction {
    Logger logger = LoggerFactory.getLogger(CheckTransaction.class);

    IGetTransactions getTransactions;

    @Autowired
    public CheckTransaction(IGetTransactions getTransactions) {
        this.getTransactions = getTransactions;
    }

    @Override
    public List<TransactionDTO> getTransaction() {
        logger.info("GET TRANSACTIONS");
        return this.getTransactions.getAllTransactions();
    }
}