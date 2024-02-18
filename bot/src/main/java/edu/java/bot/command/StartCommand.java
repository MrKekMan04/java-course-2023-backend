package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.UserChat;
import edu.java.bot.repository.UserChatRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private static final String COMMAND_NAME = "/start";
    private static final String COMMAND_DESCRIPTION = "Зарегистрировать пользователя";
    private final UserChatRepository repository;

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

        if (repository.findById(chat.id()) == null) {
            repository.add(new UserChat(chat.id(), new ArrayList<>()));
        }

        return new SendMessage(
            chat.id(),
            "Привет, <b>%s</b>!\nПосмотреть доступные команды можно при помощи команды /help".formatted(chat.username())
        ).parseMode(ParseMode.HTML);
    }
}
