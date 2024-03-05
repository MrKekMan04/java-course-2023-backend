package edu.java.bot.command;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartCommandTest extends CommandTest {
    private Command startCommand;

    @Override
    public void setUp() {
        super.setUp();

        startCommand = new StartCommand(client);
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
        Mockito.doReturn(Mono.just(ResponseEntity.ok().build())).when(client).registerChat(chatId);

        SendMessage handle = startCommand.handle(update);

        assertTrue(handle.getParameters().get("text").toString().contains("Вы успешно были зарегистрированы!"));
    }
}
