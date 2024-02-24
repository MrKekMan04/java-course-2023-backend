package edu.java.service.client;

import edu.java.entity.dto.ApiErrorResponse;
import edu.java.entity.dto.LinkUpdateRequest;
import edu.java.exception.ApiErrorResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class BotClient extends BaseClient {
    public BotClient(String baseUrl) {
        super(baseUrl);
    }

    public Mono<ResponseEntity<Void>> sendUpdate(LinkUpdateRequest request) {
        return client.post()
            .uri("/updates")
            .bodyValue(request)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .toBodilessEntity();
    }
}
