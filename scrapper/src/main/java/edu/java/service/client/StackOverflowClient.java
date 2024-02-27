package edu.java.service.client;

import edu.java.entity.dto.StackOverflowResponseDTO;
import reactor.core.publisher.Mono;

public class StackOverflowClient extends BaseClient {
    public StackOverflowClient(String baseUrl) {
        super(baseUrl);
    }

    public Mono<StackOverflowResponseDTO> getQuestionsInfo(String ids) {
        return client.get()
            .uri("/questions/" + ids + "?site=stackoverflow")
            .retrieve()
            .bodyToMono(StackOverflowResponseDTO.class);
    }
}
