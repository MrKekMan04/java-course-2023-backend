package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.UserChat;
import edu.java.bot.repository.UserChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final UserChatRepository chatRepository;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        UserChat userChat = chatRepository.findById(chatId);

        List<String> trackingLinks = userChat.getTrackingLinks();

        if (trackingLinks.isEmpty()) {
            return new SendMessage(chatId, "Вы не отслеживаете ни одной ссылки");
        }

        return new SendMessage(chatId, buildResponse(trackingLinks));
    }

    private String buildResponse(List<String> links) {
        StringBuilder responseMessage = new StringBuilder()
            .append("Отслеживаемые ссылки:\n");

        for (int i = 0; i < links.size(); i++) {
            responseMessage.append(i + 1).append(": ").append(links.get(i)).append("\n");
        }

        return responseMessage.toString();
    }
}
