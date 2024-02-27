package edu.java.service.client;

public class StackOverflowClientBuilder {
    private String baseUrl = "https://api.stackexchange.com/2.3";

    public StackOverflowClientBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public StackOverflowClient build() {
        return new StackOverflowClient(baseUrl);
    }
}
