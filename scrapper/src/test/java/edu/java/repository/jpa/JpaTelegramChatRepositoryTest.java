package edu.java.repository.jpa;

import edu.java.entity.TelegramChat;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JpaTelegramChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaTelegramChatRepository telegramChatRepository;

    @DynamicPropertySource
    public static void setJpaAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddWorksRight() {
        final TelegramChat telegramChat = new TelegramChat()
            .setId(123L)
            .setRegisteredAt(OffsetDateTime.now());

        assertEquals(telegramChatRepository.findAll(), List.of());
        telegramChatRepository.save(telegramChat);
        assertEquals(telegramChatRepository.findAll(), List.of(telegramChat));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatRemoveWorksRight() {
        final TelegramChat telegramChat = new TelegramChat()
            .setId(123L)
            .setRegisteredAt(OffsetDateTime.now());

        assertEquals(telegramChatRepository.findAll(), List.of());
        telegramChatRepository.save(telegramChat);
        telegramChatRepository.delete(telegramChat);
        assertEquals(telegramChatRepository.findAll(), List.of());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatFindAllWorksRight() {
        final List<TelegramChat> chats = List.of(
            new TelegramChat().setId(1L).setRegisteredAt(OffsetDateTime.now()),
            new TelegramChat().setId(2L).setRegisteredAt(OffsetDateTime.now())
        );

        telegramChatRepository.saveAll(chats);
        assertEquals(chats, telegramChatRepository.findAll());
    }
}
