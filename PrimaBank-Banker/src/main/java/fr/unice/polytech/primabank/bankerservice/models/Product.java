package fr.unice.polytech.primabank.bankerservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Map;

/**
 * @author damedomey
 */
@Entity(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    private String description;
    private double price;
    // The payment mode, per month ou year
    private String mode;

    public static Product fromJson(Map<String, String> jsonMap) {
        Product product = new Product();
        product.setName(jsonMap.get("name"));
        product.setDescription(jsonMap.get("description"));
        product.setPrice(Double.parseDouble(jsonMap.get("price")));
        product.setMode(jsonMap.get("mode"));
        return product;
    }
}
