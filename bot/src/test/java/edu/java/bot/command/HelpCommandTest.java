package edu.java.bot.command;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest extends CommandTest {
    private Command helpCommand;

    @Override
    public void setUp() {
        super.setUp();

        helpCommand = new HelpCommand(List.of(new StartCommand(userChatRepository)));
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/help", helpCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Вывести окно с командами", helpCommand.description());
    }

    @Test
    public void assertThatHandleReturnedRightString() {
        final String expected = "Доступные команды:\n<b>/start</b> - Зарегистрировать пользователя\n";

        assertEquals(expected, helpCommand.handle(update).getParameters().get("text"));
    }
}
