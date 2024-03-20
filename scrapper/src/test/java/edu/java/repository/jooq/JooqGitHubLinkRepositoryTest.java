package edu.java.repository.jooq;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class JooqGitHubLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqGitHubLinkRepository gitHubLinkRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void assertThatGetNonExistsGitHubLinkThrowsException() {
        final Link nonExistsLink = new Link()
            .setId(Long.MAX_VALUE)
            .setUrl(URI.create("https://url.com"))
            .setLastUpdatedAt(OffsetDateTime.now());

        assertNull(gitHubLinkRepository.getLink(nonExistsLink));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddExistsLinkReturnedRightResult() {
        final Link existsLink = Objects.requireNonNull(jdbcTemplate.queryForObject(
            "INSERT INTO link (url, last_updated_at) VALUES (?, ?) RETURNING *",
            new BeanPropertyRowMapper<>(Link.class),
            URI.create("https://link.ru").toString(),
            OffsetDateTime.now()
        ));

        GitHubLink gitHubLink = new GitHubLink()
            .setDefaultBranch("master")
            .setForksCount(1L);
        gitHubLink.setId(existsLink.getId());
        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.addLink(gitHubLink));

        assertEquals(gitHubLink.getId(), savedGitHubLink.getId());
        assertEquals(gitHubLink.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertEquals(gitHubLink.getForksCount(), savedGitHubLink.getForksCount());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateGitHubLinkWorksRight() {
        final Link existsLink = Objects.requireNonNull(jdbcTemplate.queryForObject(
            "INSERT INTO link (url, last_updated_at) VALUES (?, ?) RETURNING *",
            new BeanPropertyRowMapper<>(Link.class),
            URI.create("https://link.ru").toString(),
            OffsetDateTime.now()
        ));

        final GitHubLink gitHubLink = new GitHubLink()
            .setDefaultBranch("master")
            .setForksCount(2L);
        gitHubLink.setId(existsLink.getId());

        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.addLink(gitHubLink));

        savedGitHubLink.setForksCount(3L);
        gitHubLinkRepository.updateLink(savedGitHubLink);

        assertEquals(gitHubLink.getId(), savedGitHubLink.getId());
        assertEquals(gitHubLink.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertNotEquals(gitHubLink.getForksCount(), savedGitHubLink.getForksCount());
    }
}
