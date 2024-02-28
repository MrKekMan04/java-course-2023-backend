package edu.java.bot.service.client;

public class ScrapperClientBuilder {
    private String baseUrl = "http://localhost:8080";

    public ScrapperClientBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ScrapperClient build() {
        return new ScrapperClient(baseUrl);
    }
}
