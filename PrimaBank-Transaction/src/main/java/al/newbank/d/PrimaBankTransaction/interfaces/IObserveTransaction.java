package al.newbank.d.PrimaBankTransaction.interfaces;

import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;

import java.util.List;

public interface IObserveTransaction {
    List<TransactionDTO> getTransaction();
}