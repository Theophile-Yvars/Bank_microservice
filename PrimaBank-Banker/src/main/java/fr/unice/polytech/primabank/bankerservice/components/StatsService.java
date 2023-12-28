package fr.unice.polytech.primabank.bankerservice.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class StatsService {

    private String statsUrl = "http://stats:9000/stats";
    private Logger logger = LoggerFactory.getLogger(StatsService.class);

    public ResponseEntity<String> askStatistics() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> stats = restTemplate.getForEntity(statsUrl + "/fetch-redis", String.class);
        logger.info("Stats : " + stats);
        return stats;
}
    
    
}
