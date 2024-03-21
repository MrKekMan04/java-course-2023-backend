package edu.java.repository.jooq;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JooqStackOverflowLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqStackOverflowLinkRepository stackOverflowLinkRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void assertThatGetNonExistsStackOverflowLinkThrowsException() {
        final Link nonExistsLink = new Link()
            .setId(Long.MAX_VALUE)
            .setUrl(URI.create("https://url.com"))
            .setLastUpdatedAt(OffsetDateTime.now());

        assertNull(stackOverflowLinkRepository.getLink(nonExistsLink));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddStackOverflowLinkReturnedRightResult() {
        final Link existsLink = Objects.requireNonNull(jdbcTemplate.queryForObject(
            "INSERT INTO link (url, last_updated_at) VALUES (?, ?) RETURNING *",
            new BeanPropertyRowMapper<>(Link.class),
            URI.create("https://link.ru").toString(),
            OffsetDateTime.now()
        ));

        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(1L)
            .setScore(12L);
        stackOverflowLink.setId(existsLink.getId());

        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.addLink(stackOverflowLink));

        assertEquals(stackOverflowLink.getId(), savedStackOverflowLink.getId());
        assertEquals(stackOverflowLink.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertEquals(stackOverflowLink.getScore(), savedStackOverflowLink.getScore());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddStackOverflowLinkWhenReferenceLinkIsNotExistsThrowsException() {
        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(0L)
            .setScore(1L);
        stackOverflowLink.setId(Long.MAX_VALUE);

        assertThrows(
            DataIntegrityViolationException.class,
            () -> stackOverflowLinkRepository.addLink(stackOverflowLink)
        );
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateStackOverflowLinkWorksRight() {
        final Link existsLink = Objects.requireNonNull(jdbcTemplate.queryForObject(
            "INSERT INTO link (url, last_updated_at) VALUES (?, ?) RETURNING *",
            new BeanPropertyRowMapper<>(Link.class),
            URI.create("https://link.ru").toString(),
            OffsetDateTime.now()
        ));

        StackOverflowLink stackOverflowLink = new StackOverflowLink()
            .setAnswerCount(1L)
            .setScore(12L);
        stackOverflowLink.setId(existsLink.getId());

        StackOverflowLink savedStackOverflowLink =
            Objects.requireNonNull(stackOverflowLinkRepository.addLink(stackOverflowLink));
        savedStackOverflowLink.setScore(1L);

        stackOverflowLinkRepository.updateLink(savedStackOverflowLink);

        assertEquals(stackOverflowLink.getId(), savedStackOverflowLink.getId());
        assertEquals(stackOverflowLink.getAnswerCount(), savedStackOverflowLink.getAnswerCount());
        assertNotEquals(stackOverflowLink.getScore(), savedStackOverflowLink.getScore());
    }
}
