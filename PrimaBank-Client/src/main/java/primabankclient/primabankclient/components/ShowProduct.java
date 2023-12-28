package primabankclient.primabankclient.components;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import primabankclient.primabankclient.controllers.dto.AnalysisDto;
import primabankclient.primabankclient.controllers.dto.ProductDto;
import primabankclient.primabankclient.entities.Analytics;
import primabankclient.primabankclient.entities.Product;
import primabankclient.primabankclient.repositories.ClientRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ShowProduct {

    public String analyticsUrl = "http://analysis:8080/offers/";

    public RestTemplate restTemplate = new RestTemplate();


    @Autowired
    ClientRepository clientRepository;

    public List<ProductDto> getAdvertisementForClient(Long clientId) {
        // Utilisez un tableau pour désérialiser la réponse JSON
        ProductDto[] products = restTemplate.getForObject(analyticsUrl + "/getOffers/" + clientId, ProductDto[].class);

        // Convertissez le tableau en une liste
        List<ProductDto> productList = Arrays.asList(products);

        return productList;
    }
}
