package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private static final String COMMAND_NAME = "/help";
    private static final String COMMAND_DESCRIPTION = "Вывести окно с командами";
    private final List<Command> commands;

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
        return new SendMessage(update.message().chat().id(), buildResponse())
            .parseMode(ParseMode.HTML);
    }

    private String buildResponse() {
        StringBuilder responseMessage = new StringBuilder()
            .append("Доступные команды:\n");

        commands.forEach(command -> responseMessage.append("<b>%s</b> - %s\n".formatted(
            command.command(),
            command.description()
        )));

        return responseMessage.toString();
    }
}
