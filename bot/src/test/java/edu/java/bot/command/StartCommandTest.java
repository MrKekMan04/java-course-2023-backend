package edu.java.bot.command;

import edu.java.bot.entity.UserChat;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StartCommandTest extends CommandTest {
    private Command startCommand;

    @Override
    public void setUp() {
        super.setUp();

        startCommand = new StartCommand(userChatRepository);
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/start", startCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Зарегистрировать пользователя", startCommand.description());
    }

    @Test
    public void assertThatNewUserAddInRepository() {
        startCommand.handle(update);

        UserChat userChat = userChatRepository.findById(chatId);

        assertNotNull(userChat);
        assertEquals(chatId, userChat.getChatId());
        assertEquals(List.of(), userChat.getTrackingLinks());
    }
}
