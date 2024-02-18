package edu.java.bot.command;

import edu.java.bot.entity.UserChat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnTrackCommandTest extends CommandTest {
    private Command unTrackCommand;

    @Override
    public void setUp() {
        super.setUp();

        unTrackCommand = new UnTrackCommand(userChatRepository);
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/untrack", unTrackCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Прекратить отслеживание ссылки", unTrackCommand.description());
    }

    @Test
    public void assertThatWrongSyntaxReturnedRightResponse() {
        Mockito.doReturn("/untrack").when(message).text();

        assertEquals("Неверный синтаксис команды", unTrackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatIncorrectLinkReturnedRightResponse() {
        Mockito.doReturn("/untrack link").when(message).text();

        assertEquals("Ссылка некорректна", unTrackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatUnTrackExistingLinkReturnedRightResponse() {
        Mockito.doReturn("/untrack https://github.com").when(message).text();
        userChatRepository.add(new UserChat(chatId, new ArrayList<>(List.of("https://github.com"))));

        assertEquals(
            "Ссылка успешно удалена из отслеживаемых",
            unTrackCommand.handle(update).getParameters().get("text")
        );
    }

    @Test
    public void assertThatUnTrackNotExistingLinkReturnedRightResponse() {
        Mockito.doReturn("/untrack https://github.com").when(message).text();
        userChatRepository.add(new UserChat(chatId, new ArrayList<>()));

        assertEquals("Ссылка не отслеживается", unTrackCommand.handle(update).getParameters().get("text"));
    }
}
