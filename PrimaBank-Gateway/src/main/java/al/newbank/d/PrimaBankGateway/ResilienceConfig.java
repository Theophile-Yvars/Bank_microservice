package al.newbank.d.PrimaBankGateway;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

@Configuration
public class ResilienceConfig {

    public static final String CIRCUIT_BREAKER_CONFIG_NAME = "primabankCircuitBreaker";
    public static final String RETRY_CONFIG_NAME = "primabankCircuitBreaker";
    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerConfig.class);

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public ResilienceConfig(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Bean
    public CircuitBreaker circuitBreaker() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_CONFIG_NAME, circuitBreakerConfig());

        circuitBreaker.getEventPublisher()
                .onSuccess(event -> LOGGER.info("CircuitBreaker onSuccess: {}", event))
                .onError(event -> LOGGER.error("CircuitBreaker onError: {}", event))
                .onStateTransition(event -> LOGGER.info("CircuitBreaker onStateTransition: {}", event))
                .onCallNotPermitted(event -> LOGGER.warn("CircuitBreaker onCallNotPermitted: {}", event))
                .onReset(event -> LOGGER.info("CircuitBreaker onReset: {}", event))
                .onIgnoredError(event -> LOGGER.info("CircuitBreaker onIgnoredError: {}", event));

        return circuitBreaker;
    }

    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .slidingWindowSize(10)
                .slidingWindowType(COUNT_BASED)
                .minimumNumberOfCalls(4)
                .failureRateThreshold(50)
                .slowCallRateThreshold(100)
                .slowCallDurationThreshold(Duration.ofMillis(30000))
                .waitDurationInOpenState(Duration.ofMillis(10000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
    }

    @Bean
    public RetryRegistry configureRetryRegistry() {
        final RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .intervalFunction(IntervalFunction.ofExponentialBackoff(IntervalFunction.DEFAULT_INITIAL_INTERVAL, 2)) // OR this
                .build();

        return RetryRegistry.of(Map.of(RETRY_CONFIG_NAME, retryConfig));
    }
}