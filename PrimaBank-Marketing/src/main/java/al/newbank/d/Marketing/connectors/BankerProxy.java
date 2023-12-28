package al.newbank.d.Marketing.connectors;

import al.newbank.d.Marketing.components.Analyzer;
import al.newbank.d.Marketing.dto.ProductDTO;
import al.newbank.d.Marketing.interfaces.IBanker;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BankerProxy implements IBanker{
    private Logger logger = LoggerFactory.getLogger(BankerProxy.class);
    private RestTemplate restTemplate = new RestTemplate();
    @Value("${banker.host.baseurl}")
    private String bankerURI;

    @Override
    public List<ProductDTO> getAllProducts() {
        this.logger.info("Get all products");
        List<ProductDTO> productDTOList = new ArrayList<>();

        try {
            String url = bankerURI + "/bankers/getProduces";

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            List<Map<String, Object>> responseBody = response.getBody();

            for (Map<String, Object> productMap : responseBody) {
                ProductDTO productDTO = new ProductDTO(
                        String.valueOf(productMap.get("id")),
                        String.valueOf(productMap.get("name")),
                        String.valueOf(productMap.get("description")),
                        (Double) productMap.get("price"),
                        String.valueOf(productMap.get("mode"))
                );
                productDTOList.add(productDTO);
            }

            return productDTOList;
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return new ArrayList<>();
            }
            throw errorException;
        }
    }


}
