package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Bot extends TelegramBot {
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<Command> commands;

    public Bot(ApplicationConfig config, List<Command> commands) {
        super(config.telegramToken());

        this.commands = commands;
        this.setUpdatesListener(this::onTelegramUpdateReceived);
    }

    private int onTelegramUpdateReceived(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null) {
                Long chatId = update.message().chat().id();
                String message = update.message().text();

                if (message.startsWith("/")) {
                    processCommandMessage(update, chatId, message);
                } else {
                    processNonCommandMessage(chatId, message);
                }
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processCommandMessage(Update update, Long chatId, String message) {
        LOGGER.info("[Chat id: %s] process command: %s".formatted(chatId, message));
        boolean isCommandFind = false;

        for (Command command : commands) {
            if (message.startsWith(command.command())) {
                isCommandFind = true;
                execute(command.handle(update));
            }
        }

        if (!isCommandFind) {
            execute(new SendMessage(chatId, "Неизвестная команда. Доступные команды: /help"));
        }
    }

    private void processNonCommandMessage(Long chatId, String message) {
        LOGGER.info("[Chat id: %s] process text: %s".formatted(chatId, message));
        execute(new SendMessage(chatId, "Введите команду. Доступные команды: /help"));
    }
}
