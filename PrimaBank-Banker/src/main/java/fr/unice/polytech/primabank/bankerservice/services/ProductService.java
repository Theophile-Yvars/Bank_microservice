package fr.unice.polytech.primabank.bankerservice.services;

import fr.unice.polytech.primabank.bankerservice.models.Product;
import fr.unice.polytech.primabank.bankerservice.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author damedomey
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        List<Product> products = this.productRepository.findAll();
        logger.info("Produces : " + products);
        return  products;
    }
}
