package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.client.ScrapperClient;
import edu.java.bot.service.client.ScrapperClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public abstract class CommandTest {
    protected Update update;
    protected Message message;
    protected Long chatId;
    protected ScrapperClient client;

    @BeforeEach
    public void setUp() {
        update = Mockito.spy(new Update());
        message = Mockito.spy(new Message());
        Chat chat = Mockito.spy(new Chat());
        chatId = 123L;
        client = Mockito.spy(new ScrapperClientBuilder().build());

        Mockito.doReturn(message).when(update).message();
        Mockito.doReturn(chat).when(message).chat();
        Mockito.doReturn(chatId).when(chat).id();
    }
}
