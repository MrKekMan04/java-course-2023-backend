package edu.java.bot.command;

import edu.java.bot.entity.UserChat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest extends CommandTest {
    private Command listCommand;

    @Override
    public void setUp() {
        super.setUp();

        listCommand = new ListCommand(userChatRepository);
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/list", listCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Показать список отслеживаемых ссылок", listCommand.description());
    }

    @Test
    public void assertThatEmptyListOfTrackingLinksHandleReturnedRightString() {
        Mockito.doReturn(new UserChat(1L, List.of())).when(userChatRepository).findById(Mockito.any(Long.class));

        assertEquals("Вы не отслеживаете ни одной ссылки", listCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatNotEmptyListOfTrackingLinksHandleReturnedRightString() {
        Mockito.doReturn(new UserChat(1L, List.of("link"))).when(userChatRepository).findById(Mockito.any(Long.class));

        assertEquals("Отслеживаемые ссылки:\n1: link\n", listCommand.handle(update).getParameters().get("text"));
    }
}
