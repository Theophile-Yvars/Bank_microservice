package al.newbank.d.Saver.controllers;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import al.newbank.d.Saver.components.TransactionExpiration;
import al.newbank.d.Saver.dto.TransactionDTO;
import al.newbank.d.Saver.entities.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import al.newbank.d.Saver.repositories.TransactionRepository;

@RestController("/saver")
public class TransactionController {


    @Autowired
    TransactionExpiration expirationService;


    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionRepository transactionRepository;

    @KafkaListener(topics = "primabanktransaction", groupId = "primaBank-transaction-saver")
    void listener(String data) {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("KAFKA LISTENER : topic primabanktransaction");
        logger.info("Data : " + data);

        // Utilisez une expression régulière pour extraire les valeurs
        Pattern pattern = Pattern.compile("name='(.*?)', account=(\\d+), price=(\\d+\\.\\d+), country='(.*?)', type='(.*?)', origine='(.*?)', tagetAccount=(\\d+)");
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            String name = matcher.group(1);
            Long account = Long.parseLong(matcher.group(2));
            double price = Double.parseDouble(matcher.group(3));
            String country = matcher.group(4);
            String type = matcher.group(5);
            String origine = matcher.group(6);
            Long tagetAccount = Long.parseLong(matcher.group(7));

            // Créez l'objet TransactionDTO
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setName(name);
            transactionDTO.setAccount(account);
            transactionDTO.setPrice(price);
            transactionDTO.setCountry(country);
            transactionDTO.setType(type);
            transactionDTO.setOrigine(origine);
            transactionDTO.setTagetAccount(tagetAccount);

            transactionRepository.save(this.transactionDtoToEntity(transactionDTO));

            logger.info("Transaction DTO : " + transactionDTO.toString());
        }else{
            logger.info("Pas compatible avec transaction dto");
        }
    }

    @GetMapping("/fetch-slave")
    public String fetchSlave(){
        return transactionRepository.findAll().toString();
    }

    private Transaction transactionDtoToEntity(TransactionDTO transactionDTO){
        Transaction transaction = new Transaction(
                transactionDTO.getName(),
                transactionDTO.getAccount(),
                transactionDTO.getPrice(),
                transactionDTO.getCountry(),
                transactionDTO.getType(),
                transactionDTO.getOrigine(),
                transactionDTO.getTagetAccount()
        );
        return transaction;
    }
    

    @Scheduled(cron = "0 0 0 * * 5")
    public void deleteOldTransactions() {
        // Calculer la date d'il y a 3 mois
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        // Appeler la méthode de service pour supprimer les transactions
        expirationService.deleteTransactionsOlderThan(threeMonthsAgo);
    }
}
