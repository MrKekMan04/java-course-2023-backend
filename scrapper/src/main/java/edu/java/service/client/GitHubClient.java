package edu.java.service.client;

import edu.java.entity.dto.GitHubResponseDTO;
import org.springframework.retry.support.RetryTemplate;
import reactor.core.publisher.Mono;

public class GitHubClient extends BaseClient {
    private final RetryTemplate retryTemplate;

    public GitHubClient(String baseUrl, RetryTemplate retryTemplate) {
        super(baseUrl);

        this.retryTemplate = retryTemplate;
    }

    public Mono<GitHubResponseDTO> getUserRepository(String repositoryPath) {
        return retryTemplate.execute(context -> client.get()
            .uri("/repos/" + repositoryPath)
            .retrieve()
            .bodyToMono(GitHubResponseDTO.class));
    }
}
