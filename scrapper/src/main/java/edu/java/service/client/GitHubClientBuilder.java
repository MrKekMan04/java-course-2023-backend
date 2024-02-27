package edu.java.service.client;

public class GitHubClientBuilder {
    private String baseUrl = "https://api.github.com";

    public GitHubClientBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public GitHubClient build() {
        return new GitHubClient(baseUrl);
    }
}
