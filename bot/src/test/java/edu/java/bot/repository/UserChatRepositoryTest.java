package edu.java.bot.repository;

import edu.java.bot.entity.UserChat;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class UserChatRepositoryTest {
    @Test
    public void assertThatRepositoryWorksRight() {
        UserChatRepository repository = new UserChatRepository();
        long chatId = 1L;
        List<String> trackingLinks = List.of();

        assertNull(repository.findById(chatId));

        UserChat userChat = new UserChat(chatId, trackingLinks);
        repository.add(userChat);

        assertSame(userChat, repository.findById(chatId));
    }
}
