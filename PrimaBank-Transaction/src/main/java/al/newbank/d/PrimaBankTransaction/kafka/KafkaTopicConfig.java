package al.newbank.d.PrimaBankTransaction.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

class KafkaTopicConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("primabanktransaction").build();
    }
}
