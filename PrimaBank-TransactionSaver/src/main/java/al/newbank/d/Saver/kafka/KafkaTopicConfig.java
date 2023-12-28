package al.newbank.d.Saver.kafka;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("primabanktransaction").build();
    }


}