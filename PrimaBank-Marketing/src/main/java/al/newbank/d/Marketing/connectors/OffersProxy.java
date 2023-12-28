package al.newbank.d.Marketing.connectors;

import al.newbank.d.Marketing.dto.ProductDTO;
import al.newbank.d.Marketing.interfaces.IOffers;
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
public class OffersProxy implements IOffers {
    private Logger logger = LoggerFactory.getLogger(OffersProxy.class);
    private RestTemplate restTemplate = new RestTemplate();
    @Value("${offer.host.baseurl}")
    private String bankerURI;


    @Override
    public boolean delete() {
        this.logger.info("delete ..");
        try {
            String url = bankerURI + "/offers/delete";

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Boolean>() {}
            );
            return true;
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return false;
            }
            logger.error(errorException.toString());
            return false;
        }
    }
}
