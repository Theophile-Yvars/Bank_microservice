package fr.unice.polytech.primabank.bankerservice.components;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class ClientsService {

    private String clientsUrl = "http://localhost:8082/clients";

    public String[] getClients() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(clientsUrl + "/getAllAccounts", String[].class);    
    }
    
    
}
