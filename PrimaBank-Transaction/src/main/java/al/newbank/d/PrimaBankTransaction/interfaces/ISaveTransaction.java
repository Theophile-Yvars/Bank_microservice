package al.newbank.d.PrimaBankTransaction.interfaces;

import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;

public interface ISaveTransaction {
    boolean save(TransactionDTO transactionDTO);
}