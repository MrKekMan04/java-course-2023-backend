package edu.java.repository.jpa;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@DirtiesContext
public class JpaStackOverflowLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaStackOverflowLinkRepository stackOverflowLinkRepository;

    @DynamicPropertySource
    public static void setJpaAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatGetNonExistsStackOverflowLinkThrowsException() {
        final Link nonExistsLink = new Link()
            .setId(Long.MAX_VALUE)
            .setUrl(URI.create("https://url.com"))
            .setLastUpdatedAt(OffsetDateTime.now());

        assertEquals(Optional.empty(), stackOverflowLinkRepository.findById(nonExistsLink.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddStackOverflowLinkReturnedRightResult() {
        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(1L)
            .setScore(12L);
        stackOverflowLink.setId(145L);
        stackOverflowLink.setUrl(URI.create("https://link.ru"));
        stackOverflowLink.setLastUpdatedAt(OffsetDateTime.now());

        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.save(stackOverflowLink));

        assertEquals(stackOverflowLink.getId(), savedStackOverflowLink.getId());
        assertEquals(stackOverflowLink.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertEquals(stackOverflowLink.getScore(), savedStackOverflowLink.getScore());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateStackOverflowLinkWorksRight() {
        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(1L)
            .setScore(12L);
        stackOverflowLink.setId(4132L);
        stackOverflowLink.setUrl(URI.create("https://link.ru"));
        stackOverflowLink.setLastUpdatedAt(OffsetDateTime.now());

        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.save(stackOverflowLink));
        savedStackOverflowLink.setScore(1L);

        stackOverflowLinkRepository.save(savedStackOverflowLink);

        assertEquals(stackOverflowLink.getId(), savedStackOverflowLink.getId());
        assertEquals(stackOverflowLink.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertNotEquals(stackOverflowLink.getScore(), savedStackOverflowLink.getScore());
    }
}
