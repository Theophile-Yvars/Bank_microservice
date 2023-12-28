package al.newbank.d.PrimaBankTransaction.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import al.newbank.d.PrimaBankTransaction.RabbitConstants;

@Configuration
public class RabbitConfig {
    
    @Bean
    Queue queue() {
        return new Queue(RabbitConstants.TRANSACTIONS_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RabbitConstants.PRIMABANK_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitConstants.TRANSACTIONS_ROUTING_KEY);
    }
}
