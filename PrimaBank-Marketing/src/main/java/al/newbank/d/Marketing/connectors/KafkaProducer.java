package al.newbank.d.Marketing.connectors;

import al.newbank.d.Marketing.dto.AnalysisDTO;
import al.newbank.d.Marketing.interfaces.IProduceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer implements IProduceEvent {
    private Logger logger = LoggerFactory.getLogger(BankerProxy.class);
    private String topicName = "primabankoffers";

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(AnalysisDTO analysisDTO) {
        logger.info("SEND MESSAGE : " + String.valueOf(analysisDTO) + ". Topic : " + topicName);
        kafkaTemplate.send(topicName, String.valueOf(analysisDTO));
    }
}
