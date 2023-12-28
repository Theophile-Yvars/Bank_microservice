package fr.unice.polytech.primabank.bankerservice.components;

import fr.unice.polytech.primabank.bankerservice.models.Product;
import fr.unice.polytech.primabank.bankerservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.Console;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Populate the product table in database if it's empty
 * The data are loaded from resources/data/products.yml
 * This implementation don't take in consideration the environment due
 * to the fact that the product data must be defined by another system.
 *
 * @author damedomey
 */
@Component
public class ProductDataLoader implements CommandLineRunner {
    private final ProductRepository productRepository;

    public ProductDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            loadProductsFromYaml();
        }
    }

    private void loadProductsFromYaml() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new ClassPathResource("data/products.yml").getInputStream()) {

            List<Map<String, String>> products = yaml.load(inputStream);

            List<Product> productsToSave = products.stream()
                    .map(Product::fromJson)
                    .toList();

            // Save products to the database
            productRepository.saveAll(productsToSave);

        } catch (Exception e) {
            System.err.println("Failed to load data from products.yml");
            e.printStackTrace();
        }
    }
}
