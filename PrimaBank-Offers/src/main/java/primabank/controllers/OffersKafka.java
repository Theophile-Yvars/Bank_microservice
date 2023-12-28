package primabank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import primabank.dto.AnalysisDTO;
import primabank.dto.ProductDTO;
import primabank.entities.Offer;
import primabank.repositories.OffersRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class OffersKafka {
    private Logger logger = LoggerFactory.getLogger(OffersKafka.class);

    @Autowired
    OffersRepository offerRepository;

    @KafkaListener(topics = "primabankoffers", groupId = "primaBank-statistics")
    void listener(String data) {

        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("KAFKA LISTNER : topic primabankoffers");
        logger.info("Data : " + data);


        //String input = "AnalysisDTO{idClient='213', product=ProductDTO{id='123', name='test', description='test', price=123.0, mode='test'}}";

        // Utilisez des expressions régulières pour extraire les valeurs
        Pattern analysisPattern = Pattern.compile("AnalysisDTO\\{idClient='(\\d+)', product=ProductDTO\\{id='(\\d+)', name='(.*?)', description='(.*?)', price=(\\d+\\.\\d+), mode='(.*?)'}}");
        Matcher analysisMatcher = analysisPattern.matcher(data);

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
            System.out.println("Hamza");
            System.out.println(this.transformAnalysisDTOToOffer(analysisDTO));
            this.offerRepository.save(this.transformAnalysisDTOToOffer(analysisDTO));
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