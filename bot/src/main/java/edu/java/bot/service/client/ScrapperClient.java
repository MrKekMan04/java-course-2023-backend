package edu.java.bot.service.client;

import edu.java.bot.entity.dto.AddLinkRequest;
import edu.java.bot.entity.dto.ApiErrorResponse;
import edu.java.bot.entity.dto.LinkResponse;
import edu.java.bot.entity.dto.ListLinksResponse;
import edu.java.bot.entity.dto.RemoveLinkRequest;
import edu.java.bot.exception.ApiErrorResponseException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class ScrapperClient extends BaseClient {
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private static final String TG_CHAT_CONTROLLER_URI = "/tg-chat/{id}";
    private static final String LINK_CONTROLLER_URI = "/links";

    public ScrapperClient(String baseUrl) {
        super(baseUrl);
    }

    public Mono<ResponseEntity<Void>> registerChat(Long tgChatId) {
        return client.post()
            .uri(TG_CHAT_CONTROLLER_URI, tgChatId)
            .retrieve()
            .onStatus(
                statusCode -> HttpStatus.CONFLICT.equals(statusCode) || HttpStatus.BAD_REQUEST.equals(statusCode),
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .toBodilessEntity();
    }

    public Mono<ResponseEntity<Void>> deleteChat(Long tgChatId) {
        return client.delete()
            .uri(TG_CHAT_CONTROLLER_URI, tgChatId)
            .retrieve()
            .onStatus(
                statusCode -> HttpStatus.NOT_FOUND.equals(statusCode) || HttpStatus.BAD_REQUEST.equals(statusCode),
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .toBodilessEntity();
    }

    public Mono<ResponseEntity<ListLinksResponse>> getAllLinksForChat(Long tgChatId) {
        return client.get()
            .uri(LINK_CONTROLLER_URI)
            .header(TG_CHAT_ID_HEADER, tgChatId.toString())
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .toEntity(ListLinksResponse.class);
    }

    public Mono<ResponseEntity<LinkResponse>> addLink(Long tgChatId, AddLinkRequest request) {
        return client.post()
            .uri(LINK_CONTROLLER_URI)
            .header(TG_CHAT_ID_HEADER, tgChatId.toString())
            .bodyValue(request)
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .toEntity(LinkResponse.class);
    }

    public Mono<ResponseEntity<LinkResponse>> removeLink(Long tgChatId, RemoveLinkRequest request) {
        return client.method(HttpMethod.DELETE)
            .uri(LINK_CONTROLLER_URI)
            .header(TG_CHAT_ID_HEADER, tgChatId.toString())
            .bodyValue(request)
            .retrieve()
            .onStatus(
                statusCode -> HttpStatus.NOT_FOUND.equals(statusCode) || HttpStatus.BAD_REQUEST.equals(statusCode),
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiErrorResponseException::new)
            )
            .toEntity(LinkResponse.class);
    }
}