package edu.java.service.client;

import edu.java.entity.dto.StackOverflowResponseDTO;
import org.springframework.retry.support.RetryTemplate;
import reactor.core.publisher.Mono;

public class StackOverflowClient extends BaseClient {
    private final RetryTemplate retryTemplate;

    public StackOverflowClient(String baseUrl, RetryTemplate retryTemplate) {
        super(baseUrl);

        this.retryTemplate = retryTemplate;
    }

    public Mono<StackOverflowResponseDTO> getQuestionsInfo(String ids) {
        return retryTemplate.execute(context -> client.get()
            .uri("/questions/" + ids + "?site=stackoverflow")
            .retrieve()
            .bodyToMono(StackOverflowResponseDTO.class));
    }
}
