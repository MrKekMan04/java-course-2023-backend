package edu.java.bot.command;

import edu.java.bot.entity.UserChat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackCommandTest extends CommandTest {
    private Command trackCommand;

    @Override
    public void setUp() {
        super.setUp();

        trackCommand = new TrackCommand(userChatRepository);
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/track", trackCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Начать отслеживание ссылки", trackCommand.description());
    }

    @Test
    public void assertThatWrongSyntaxReturnedRightResponse() {
        Mockito.doReturn("/track").when(message).text();

        assertEquals("Неверный синтаксис команды", trackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatIncorrectLinkReturnedRightResponse() {
        Mockito.doReturn("/track link").when(message).text();

        assertEquals("Ссылка некорректна", trackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatAlreadyAddedLinkReturnedRightResponse() {
        Mockito.doReturn("/track https://github.com").when(message).text();
        userChatRepository.add(new UserChat(chatId, new ArrayList<>(List.of("https://github.com"))));

        assertEquals("Ссылка уже отслеживается", trackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatAddUniqueLinkReturnedRightResponse() {
        Mockito.doReturn("/track https://github.com").when(message).text();
        userChatRepository.add(new UserChat(chatId, new ArrayList<>()));

        assertEquals(
            "Ссылка успешно добавлена в отслеживаемые",
            trackCommand.handle(update).getParameters().get("text")
        );
    }
}
