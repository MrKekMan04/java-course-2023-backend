package edu.java.service.client;

import edu.java.entity.dto.ApiErrorResponse;
import edu.java.entity.dto.LinkUpdateRequest;
import edu.java.exception.ApiErrorResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import reactor.core.publisher.Mono;

public class BotClient extends BaseClient {
    private final RetryTemplate retryTemplate;

    public BotClient(String baseUrl, RetryTemplate retryTemplate) {
        super(baseUrl);

        this.retryTemplate = retryTemplate;
    }

    public Mono<ResponseEntity<Void>> sendUpdate(LinkUpdateRequest request) {
        return retryTemplate.execute(context -> client.post()
            .uri("/updates")
            .bodyValue(request)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .toBodilessEntity());
    }
}
