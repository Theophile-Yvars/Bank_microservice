package al.newbank.d.PrimaBankTransaction.interfaces;

import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;
import al.newbank.d.PrimaBankTransaction.utils.MessaggingTechno;

public interface IReceiveTransaction {
    boolean askSaveTransaction(TransactionDTO transactionDTO);

    void sendMessageKafka(String message, String topicName);
    void testKafka();
    void testKafkaTransaction();

    void sendMessageRabbit(String message);
    void testRabbit();
    void testRabbitTransaction();
}