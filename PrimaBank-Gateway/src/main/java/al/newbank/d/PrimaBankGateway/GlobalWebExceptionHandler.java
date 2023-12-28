package al.newbank.d.PrimaBankGateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.support.ServiceUnavailableException;
import java.net.ConnectException;

@Component
@Order(-2)
public class GlobalWebExceptionHandler implements WebExceptionHandler {
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalWebExceptionHandler.class);

    public GlobalWebExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        String path = exchange.getRequest().getPath().value();

        if (ex instanceof ConnectException || ex.getCause() instanceof ConnectException || ex instanceof ServiceUnavailableException ||ex.getCause() instanceof ServiceUnavailableException) {
            LOGGER.error("Connection refused: {}", ex.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            ErrorDto errorDTO = new ErrorDto(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service unavailable. Please try again later.", path);

            return response
                    .writeWith(Mono.fromSupplier(() -> {
                        return response.bufferFactory().wrap(toJson(errorDTO));
                    }));
        } else if (ex instanceof ResponseStatusException && ((ResponseStatusException) ex).getStatusCode() == HttpStatus.NOT_FOUND) {
            LOGGER.error("404 Resource not Found: {}", path);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_FOUND);
            ErrorDto errorDTO = new ErrorDto(HttpStatus.NOT_FOUND.value(), "The requested resource was not found.", path);

            return response
                    .writeWith(Mono.fromSupplier(() -> {
                        return response.bufferFactory().wrap(toJson(errorDTO));
                    }));
        }

        System.out.println("Unhandled exception found");
        System.err.println(ex);

        return Mono.error(ex);
    }

    private byte[] toJson(ErrorDto errorDTO) {
        try {
            return objectMapper.writeValueAsBytes(errorDTO);
        } catch (Exception e) {
            LOGGER.error("Error while writing DTO to JSON", e);
            return new byte[0];
        }
    }
}

