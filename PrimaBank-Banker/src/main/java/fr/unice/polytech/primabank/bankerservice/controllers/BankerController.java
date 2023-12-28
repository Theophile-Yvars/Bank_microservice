package fr.unice.polytech.primabank.bankerservice.controllers;

import fr.unice.polytech.primabank.bankerservice.components.StatsService;
import fr.unice.polytech.primabank.bankerservice.models.Product;
import fr.unice.polytech.primabank.bankerservice.services.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = BankerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class BankerController {
    public static final  String BASE_URI = "/bankers";
    private final ProductService productService;

    private Logger logger = LoggerFactory.getLogger(BankerController.class);

    @Autowired
    private StatsService statsService;

    public BankerController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/getProduces")
    public ResponseEntity<List<Product>> all() {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("GET PRODUCES");
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/viewStatistics")
    public ResponseEntity<String> viewStatistics() {
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("VIEW STAISTICS");
        return this.statsService.askStatistics();
    }
}