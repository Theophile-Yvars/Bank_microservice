package al.newbank.d.PrimaBankTransaction.controllers;

import al.newbank.d.PrimaBankTransaction.components.BankTransaction;
import al.newbank.d.PrimaBankTransaction.controllers.dto.TransactionDTO;
import al.newbank.d.PrimaBankTransaction.interfaces.IObserveTransaction;
import al.newbank.d.PrimaBankTransaction.interfaces.IReceiveTransaction;
import al.newbank.d.PrimaBankTransaction.utils.MessaggingTechno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.List;
import java.util.Objects;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = TransactionController.BASE_URI, produces = APPLICATION_JSON_VALUE)
@CrossOrigin
public class TransactionController {
    public static final String BASE_URI = "/transaction";
    Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TaskExecutor exec = new SimpleAsyncTaskExecutor();

    private boolean isKafka;

    @Autowired
    IReceiveTransaction receiveTransaction;

    @Autowired
    IObserveTransaction observeTransaction;

    @Autowired
    BankTransaction bankTransaction;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        logger.info("TEST");
        return ResponseEntity.ok("{status : ok}");
    }

    @GetMapping("/france/{from}/{to}")
    public ResponseEntity<String> testTransaction(@PathVariable("from") Long from, @PathVariable("to") Long to) {
        TransactionDTO transactionDTO = new TransactionDTO(
                "Test",
                from,
                52,
                "France",
                "particulier",
                "internet",
                to);
        boolean response = this.receiveTransaction.askSaveTransaction(transactionDTO);
        // this.receiveTransaction.sendMessageKafka(String.valueOf(transactionDTO),
        // "primabanktransaction");
        this.receiveTransaction.sendMessageRabbit(String.valueOf(transactionDTO));
        return ResponseEntity.ok("{SAVE : " + response + "}");
    }

    @GetMapping("/etranger/{from}/{to}")
    public ResponseEntity<String> testTransaction2(@PathVariable("from") Long from, @PathVariable("to") Long to) {
        TransactionDTO transactionDTO = new TransactionDTO(
                "Test",
                from,
                52,
                "England",
                "particulier",
                "internet",
                to);
        boolean response = this.receiveTransaction.askSaveTransaction(transactionDTO);
        // this.receiveTransaction.sendMessageKafka(String.valueOf(transactionDTO),
        // "primabanktransaction");
        this.receiveTransaction.sendMessageRabbit(String.valueOf(transactionDTO));
        return ResponseEntity.ok("{SAVE : " + response + "}");
    }

    @GetMapping("/entreprise/{from}/{to}")
    public ResponseEntity<String> testTransaction3(@PathVariable("from") Long from, @PathVariable("to") Long to) {
        TransactionDTO transactionDTO = new TransactionDTO(
                "Test",
                from,
                52,
                "England",
                "entreprise",
                "internet",
                to);
        boolean response = this.receiveTransaction.askSaveTransaction(transactionDTO);
        // this.receiveTransaction.sendMessageKafka(String.valueOf(transactionDTO),
        // "primabanktransaction");
        this.receiveTransaction.sendMessageRabbit(String.valueOf(transactionDTO));
        return ResponseEntity.ok("{SAVE : " + response + "}");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TransactionDTO>> getAll() {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("GET_ALL_TRANSACTIONS");
        return ResponseEntity.ok(this.observeTransaction.getTransaction());
    }

    @GetMapping("/getAllTransactions/{clientId}")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(@PathVariable("clientId") Long clientId) {
        List<TransactionDTO> transactions = this.observeTransaction.getTransaction();
        if (transactions != null) {
            transactions.removeIf(transaction -> !Objects.equals(transaction.getClient(), clientId));
            logger.info("");
            logger.info("==================================================================");
            logger.info("");
            logger.info("\n" + "GET_ALL_TRANSACTIONS_OF_CLIENT_" + clientId + " : " + "\n" + transactions.toString());
            return ResponseEntity.ok(transactions);
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/postTransaction")
    public ResponseEntity<String> postTransaction(@RequestBody TransactionDTO transactionDTO) {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("\n" + "POST_TRANSACTION : " + transactionDTO.toString());
        try {
            if(this.isKafka)
                this.receiveTransaction.sendMessageKafka(String.valueOf(transactionDTO), "primabanktransaction");
            else
                this.receiveTransaction.sendMessageRabbit(String.valueOf(transactionDTO));
        } catch (Exception e) {
            logger.error("Failed to send message to kafka");
            logger.error(e.getMessage());
        }
        boolean response = this.receiveTransaction.askSaveTransaction(transactionDTO);
        return ResponseEntity.ok("{SAVE : " + response + "}");
    }

    // ======================================================================
    // KAFKA TEST
    // ======================================================================

    @GetMapping("/test/kafka")
    public void testKafka() {
        this.receiveTransaction.testKafka();
    }

    @GetMapping("/test/kafka/transaction")
    public void testKafkaTransaction() {
        this.receiveTransaction.testKafkaTransaction();
    }

    // ======================================================================
    // RABBIT TEST
    // ======================================================================

    @GetMapping("/test/rabbit")
    public void testRabbit() {
        this.receiveTransaction.testRabbit();
    }

    @GetMapping("/test/rabbit/transaction")
    public void testRabbitTransaction() {
        this.receiveTransaction.testRabbitTransaction();
    }



    @GetMapping("/choose/kafka")
    public boolean chooseKafka(){
        this.logger.info("KAFKA");
        this.isKafka = true;
        return true;
    }

    @GetMapping("/choose/rabbit")
    public boolean chooseRabbit(){
        this.logger.info("RABBIT");
        this.isKafka = false;
        return true;
    }
}