package edu.java.service.client;

import edu.java.entity.dto.GitHubResponseDTO;
import reactor.core.publisher.Mono;

public class GitHubClient extends BaseClient {
    public GitHubClient(String baseUrl) {
        super(baseUrl);
    }

    public Mono<GitHubResponseDTO> getUserRepository(String repositoryPath) {
        return client.get()
            .uri("/repos/" + repositoryPath)
            .retrieve()
            .bodyToMono(GitHubResponseDTO.class);
    }
}
