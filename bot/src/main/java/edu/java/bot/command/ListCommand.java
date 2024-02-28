package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.dto.LinkResponse;
import edu.java.bot.exception.ApiErrorResponseException;
import edu.java.bot.service.client.ScrapperClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private static final String COMMAND_NAME = "/list";
    private static final String COMMAND_DESCRIPTION = "Показать список отслеживаемых ссылок";
    private final ScrapperClient client;

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        return new SendMessage(chatId, getResponseMessage(chatId))
            .disableWebPagePreview(true)
            .parseMode(ParseMode.Markdown);
    }

    private String getResponseMessage(Long chatId) {
        return client.getAllLinksForChat(chatId)
            .map(response -> {
                if (HttpStatus.OK.equals(response.getStatusCode())
                    && response.getBody() != null && response.getBody().links() != null) {
                    if (response.getBody().links().isEmpty()) {
                        return "Вы не отслеживаете ни одной ссылки";
                    }

                    return buildResponse(response.getBody().links());
                }

                return "Что-то пошло не так!";
            })
            .onErrorResume(
                ApiErrorResponseException.class,
                error -> Mono.just(error.getApiErrorResponse().description())
            )
            .block();
    }

    private String buildResponse(List<LinkResponse> linkResponses) {
        StringBuilder responseMessage = new StringBuilder()
            .append("Отслеживаемые ссылки:\n");

        List<String> urls = linkResponses.stream()
            .map(linkResponse -> linkResponse.url().toString())
            .toList();

        for (int i = 0; i < urls.size(); i++) {
            responseMessage.append(i + 1).append(": ").append(urls.get(i)).append("\n");
        }

        return responseMessage.toString();
    }
}
