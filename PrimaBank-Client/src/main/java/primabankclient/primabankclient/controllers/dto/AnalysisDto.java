package primabankclient.primabankclient.controllers.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnalysisDto {
    Long idClient;
    ProductDto product;
}
