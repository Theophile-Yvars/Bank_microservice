package primabankclient.primabankclient.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data  @AllArgsConstructor @Getter
@Setter
public class Product {

    private Long id;
    private String name;
    private String description;
    private double price;
    private String mode;
}