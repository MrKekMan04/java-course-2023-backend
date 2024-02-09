package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.entity.UserChat;
import edu.java.bot.repository.UserChatRepository;
import edu.java.bot.util.LinkUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnTrackCommand implements Command {
    private final UserChatRepository chatRepository;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        String[] parameters = update.message().text().split(" ");

        if (parameters.length != 2) {
            return new SendMessage(chatId, "Неверный синтаксис команды");
        }

        if (!LinkUtil.isLinkCorrect(parameters[1])) {
            return new SendMessage(chatId, "Ссылка некорректна");
        }

        UserChat userChat = chatRepository.findById(chatId);
        List<String> trackingLinks = userChat.getTrackingLinks();

        if (!trackingLinks.contains(parameters[1])) {
            return new SendMessage(chatId, "Ссылка не отслеживается");
        }

        trackingLinks.remove(parameters[1]);
        chatRepository.add(new UserChat(userChat.getChatId(), trackingLinks));

        return new SendMessage(chatId, "Ссылка успешно удалена из отслеживаемых");
    }
}
