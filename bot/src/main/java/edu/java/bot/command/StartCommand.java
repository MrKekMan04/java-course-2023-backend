package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.ApiErrorResponseException;
import edu.java.bot.service.client.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private static final String COMMAND_NAME = "/start";
    private static final String COMMAND_DESCRIPTION = "Зарегистрировать пользователя";
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
        Chat chat = update.message().chat();

        return new SendMessage(chat.id(), getResponseMessage(chat))
            .parseMode(ParseMode.HTML);
    }

    private String getResponseMessage(Chat chat) {
        return "Привет, <b>%s</b>!\n".formatted(chat.username())
            + client.registerChat(chat.id())
            .map(response -> {
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    return "Вы успешно были зарегистрированы!\n";
                }

                return "Что-то пошло не так!\n";
            })
            .onErrorResume(
                ApiErrorResponseException.class,
                error -> Mono.just(error.getApiErrorResponse().description() + "\n")
            )
            .block()
            + "Посмотреть доступные команды можно при помощи команды /help";
    }
}
