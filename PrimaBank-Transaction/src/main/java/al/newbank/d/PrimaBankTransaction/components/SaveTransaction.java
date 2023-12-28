package al.newbank.d.PrimaBankTransaction.components;

import al.newbank.d.PrimaBankTransaction.RabbitConstants;
import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;
import al.newbank.d.PrimaBankTransaction.interfaces.IReceiveTransaction;
import al.newbank.d.PrimaBankTransaction.interfaces.ISaveTransaction;
import al.newbank.d.PrimaBankTransaction.utils.MessaggingTechno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class SaveTransaction implements IReceiveTransaction {
    Logger logger = LoggerFactory.getLogger(SaveTransaction.class);

    private KafkaTemplate<String, String> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    ISaveTransaction saveTransaction;

    @Autowired
    public SaveTransaction(ISaveTransaction saveTransaction, KafkaTemplate<String, String> kafkaTemplate, RabbitTemplate rabbitTemplate) {
        this.saveTransaction = saveTransaction;
        this.kafkaTemplate = kafkaTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public boolean askSaveTransaction(TransactionDTO transactionDTO) {
        logger.info("ASK_SAVE_TRANSACTION" + transactionDTO.toString());
        //this.sendMessageWithCallback("coucou le test", "primabank-transaction");
        return this.saveTransaction.save(transactionDTO);
    }

    // ======================================================================
    //                          KAFKA SENDER
    // ======================================================================

    @Override
    public void testKafka() {
        this.sendMessageKafka("test 1","primabanktransaction");
        //this.sendMessageWithCallback("test 2", "sendMessageWithCallback");
    }

    @Override
    public void testKafkaTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO(
                "Test",
                12L,
                52,
                "England",
                "particulier",
                "internet",
                456L
        );

        this.sendMessageKafka(String.valueOf(transactionDTO),"primabanktransaction");
    }


    @Override
    public void sendMessageKafka(String message, String topicName) {
        logger.info("SEND MESSAGE KAFKA : " + message + ". Topic : " + topicName);
        kafkaTemplate.send(topicName, message);
    }

    // ======================================================================
    //                          KAFKA SENDER
    // ======================================================================


    @Override
    public void sendMessageRabbit(String message) {
        logger.info("SEND MESSAGE RABBIT : " + message);
        rabbitTemplate.convertAndSend(RabbitConstants.PRIMABANK_EXCHANGE, RabbitConstants.TRANSACTIONS_ROUTING_KEY, message);
    }

    @Override
    public void testRabbit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testRabbit'");
    }

    @Override
    public void testRabbitTransaction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testRabbitTransaction'");
    }
}