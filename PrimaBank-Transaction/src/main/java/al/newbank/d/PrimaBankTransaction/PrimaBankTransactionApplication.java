package al.newbank.d.PrimaBankTransaction;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.EnableAspectJAutoProxy;



@SpringBootApplication
@EnableCassandraRepositories
@EnableAspectJAutoProxy
@EnableScheduling
public class PrimaBankTransactionApplication {
	public static void main(String[] args) {
		SpringApplication.run(PrimaBankTransactionApplication.class, args);
	}

	@Bean
    public NewTopic topic1() {
        return TopicBuilder.name("primaBank").build();
    }
}