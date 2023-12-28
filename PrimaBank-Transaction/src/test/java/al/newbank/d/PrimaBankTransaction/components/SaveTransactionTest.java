package al.newbank.d.PrimaBankTransaction.components;

import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;
import al.newbank.d.PrimaBankTransaction.interfaces.IReceiveTransaction;
import al.newbank.d.PrimaBankTransaction.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class SaveTransactionTest {

    @Autowired
    IReceiveTransaction receiveTransaction;

    @Autowired
    TransactionRepository transactionRepository;

    TransactionDTO transactionDTO = new TransactionDTO(
            "Test" ,
            5L,
            52,
            "France",
            "particulier",
            "internet",
            6L
    );
    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;
/*
    @Test
    public void testSansKafka() {
        // Écrivez ici votre test sans avoir besoin de Kafka
        // Par exemple, utilisez Mockito pour simuler le comportement du KafkaTemplate
        when(kafkaTemplate.send(any(), any())).thenReturn(null);
    }*/
    @Test
    void testAskSaveTransaction(){
        int nbTransaction = this.transactionRepository.findAll().size();
        this.receiveTransaction.askSaveTransaction(this.transactionDTO);
        Assertions.assertEquals(nbTransaction + 1, this.transactionRepository.findAll().size());
    }
}