package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.dto.RemoveLinkRequest;
import edu.java.bot.exception.ApiErrorResponseException;
import edu.java.bot.service.client.ScrapperClient;
import edu.java.bot.util.LinkUtil;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UnTrackCommand implements Command {
    private static final String COMMAND_NAME = "/untrack";
    private static final String COMMAND_DESCRIPTION = "Прекратить отслеживание ссылки";
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
        String[] parameters = update.message().text().split(" ");

        if (parameters.length != 2) {
            return new SendMessage(chatId, "Неверный синтаксис команды");
        }
        URI link = LinkUtil.parse(parameters[1]);
        if (link == null) {
            return new SendMessage(chatId, "Ссылка некорректна");
        }

        return new SendMessage(chatId, getResponseMessage(chatId, link))
            .disableWebPagePreview(true)
            .parseMode(ParseMode.Markdown);
    }

    private String getResponseMessage(Long chatId, URI link) {
        return client.removeLink(chatId, new RemoveLinkRequest(link))
            .map(response -> {
                if (HttpStatus.OK.equals(response.getStatusCode())
                    && response.getBody() != null) {
                    return "[Ссылка](%s) успешно удалена из отслеживаемых"
                        .formatted(response.getBody().url());
                }

                return "Что-то пошло не так!";
            })
            .onErrorResume(
                ApiErrorResponseException.class,
                error -> Mono.just(error.getApiErrorResponse().description())
            )
            .block();
    }
}
