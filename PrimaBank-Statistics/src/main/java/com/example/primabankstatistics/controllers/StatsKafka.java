package com.example.primabankstatistics.controllers;

import com.example.primabankstatistics.components.StatsCalculator;
import com.example.primabankstatistics.dto.AnalysisDTO;
import com.example.primabankstatistics.dto.ProductDTO;
import com.example.primabankstatistics.dto.TransactionDTO;
import com.example.primabankstatistics.model.Offer;
import com.example.primabankstatistics.model.Statistic;
import com.example.primabankstatistics.model.Transaction;
import com.example.primabankstatistics.redis.Stat;
import com.example.primabankstatistics.repositories.OfferRepository;
import com.example.primabankstatistics.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class StatsKafka {
    private Logger logger = LoggerFactory.getLogger(StatsKafka.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StatsCalculator statsCalculator;
    @Autowired
    OfferRepository offerRepository;

    @KafkaListener(topics = "primabanktransaction", groupId = "primaBank-statistics")
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

            // Vous avez maintenant un objet TransactionDTO
            logger.info("TRANSACTION_SAVED");
            logger.info(transactionDTO.toString());

            // persist to redis
            statsCalculator.updateInternetstats(transactionDTO);
            statsCalculator.updateTypeStats(transactionDTO);
            statsCalculator.updateCountryStats(transactionDTO);
            logger.info("STATISTIC_SAVED");
        }else{
            logger.info("Pas compatible avec transaction dto");
        }
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

    @KafkaListener(topics = "primabankoffers", groupId = "primaBank-statistics")
    void listener2(String data) {

        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("KAFKA LISTENER : topic primabanktransaction");
        logger.info("Data : " + data);

        String input = "AnalysisDTO{idClient='213', product=ProductDTO{id='123', name='test', description='test', price=123.0, mode='test'}}";

        // Utilisez des expressions régulières pour extraire les valeurs
        Pattern analysisPattern = Pattern.compile("AnalysisDTO\\{idClient='(\\d+)', product=ProductDTO\\{id='(\\d+)', name='(.*?)', description='(.*?)', price=(\\d+\\.\\d+), mode='(.*?)'}}");
        Matcher analysisMatcher = analysisPattern.matcher(input);

        if (analysisMatcher.find()) {
            Long idClient = Long.parseLong(analysisMatcher.group(1));
            String productId = analysisMatcher.group(2);
            String productName = analysisMatcher.group(3);
            String productDescription = analysisMatcher.group(4);
            double productPrice = Double.parseDouble(analysisMatcher.group(5));
            String productMode = analysisMatcher.group(6);

            // Créez l'objet ProductDTO imbriqué
            ProductDTO productDTO = new ProductDTO(productId, productName, productDescription, productPrice, productMode);

            // Créez l'objet AnalysisDTO avec l'objet ProductDTO imbriqué
            AnalysisDTO analysisDTO = new AnalysisDTO(idClient, productDTO);

            // Vous avez maintenant un objet AnalysisDTO
            logger.info(analysisDTO.toString());

            this.offerRepository.save(this.transformAnalysisDTOToOffer(analysisDTO));
            logger.info("OFFER_SAVED");
        }
    }

    public Offer transformAnalysisDTOToOffer(AnalysisDTO analysisDTO) {
        ProductDTO productDTO = analysisDTO.getProduct();
        return new Offer(
                analysisDTO.getIdClient(),
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getMode()
        );
    }
}
