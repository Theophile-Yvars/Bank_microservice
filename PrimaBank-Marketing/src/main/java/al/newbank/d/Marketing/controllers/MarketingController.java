package al.newbank.d.Marketing.controllers;

import al.newbank.d.Marketing.dto.AnalysisDTO;
import al.newbank.d.Marketing.dto.ProductDTO;
import al.newbank.d.Marketing.interfaces.IBanker;
import al.newbank.d.Marketing.interfaces.IProduceAnalyse;
import al.newbank.d.Marketing.interfaces.IProduceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = MarketingController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class MarketingController {
    private Logger logger = LoggerFactory.getLogger(MarketingController.class);
    public static final String BASE_URI = "/marketing";

    @Autowired
    private IBanker banker;

    @Autowired
    private IProduceAnalyse produceAnalyse;

    @Autowired
    private IProduceEvent produceEvent;


    @GetMapping("/analysis")
    public ResponseEntity<String> analysis(){
        logger.info("");
        logger.info("==================================================================");
        logger.info("");

        logger.info("ANALYSIS");
        this.produceAnalyse.startAnalysis();
        return ResponseEntity.ok("{status : ok}");
    }

    @GetMapping("/test/banker")
    public ResponseEntity<String> testBankerProduct(){
        List<ProductDTO> productDTOList = banker.getAllProducts();
        logger.info("Product list : size [ ", productDTOList.size()," ]");
        for(ProductDTO productDTO : productDTOList){
            logger.info(productDTO.toString());
        }
        return ResponseEntity.ok("{status : ok}");
    }

    @GetMapping("/test/offer")
    public ResponseEntity<String> testOffer(){
        String id = "123";
        String name = "test";
        String description = "test";
        double price = 123;
        String mode = "test";
        ProductDTO productDTO = new ProductDTO(id, name, description, price, mode);
        AnalysisDTO analysisDTO = new AnalysisDTO(213L, productDTO);

        produceEvent.send(analysisDTO);

        return ResponseEntity.ok("{status : ok}");
    }
}
