package al.newbank.d.PrimaBankTransaction.components;

import al.newbank.d.PrimaBankTransaction.entities.Transaction;
import al.newbank.d.PrimaBankTransaction.interfaces.IObserveTransaction;
import al.newbank.d.PrimaBankTransaction.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class CheckTransactionTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    IObserveTransaction observeTransaction;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;
/*
    @Test
    public void testSansKafka() {
        // Écrivez ici votre test sans avoir besoin de Kafka
        // Par exemple, utilisez Mockito pour simuler le comportement du KafkaTemplate
        when(kafkaTemplate.send(any(), any())).thenReturn(null);
    }
*/

    Transaction transaction1 = new Transaction(
            "Test" ,
            7L,
            52,
            "France",
            "particulier",
            "internet",
            5L
    );

    Transaction transaction2 = new Transaction(
            "Test" ,
            7L,
            52,
            "France",
            "particulier",
            "internet",
            8L
    );


    @BeforeEach
    void initialisation() {
        transactionRepository.deleteAll();
    }

    @Test
    public void testGetTransactions() {
        Assertions.assertEquals(0, this.observeTransaction.getTransaction().size());
        this.transactionRepository.save(transaction1);
        Assertions.assertEquals(1, this.observeTransaction.getTransaction().size());
        this.transactionRepository.save(transaction2);
        Assertions.assertEquals(2, this.observeTransaction.getTransaction().size());
    }
}