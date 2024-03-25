package edu.java.repository.jpa;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class JpaGitHubLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaGitHubLinkRepository gitHubLinkRepository;

    @DynamicPropertySource
    public static void setJpaAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatGetNonExistsGitHubLinkThrowsException() {
        final Link nonExistsLink = new Link()
            .setId(Long.MAX_VALUE)
            .setUrl(URI.create("https://url.com"))
            .setLastUpdatedAt(OffsetDateTime.now());

        assertEquals(Optional.empty(), gitHubLinkRepository.findById(nonExistsLink.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatAddExistsLinkReturnedRightResult() {
        GitHubLink gitHubLink = new GitHubLink()
            .setDefaultBranch("master")
            .setForksCount(1L);
        gitHubLink.setId(51353L);
        gitHubLink.setUrl(URI.create("https://link.ru"));
        gitHubLink.setLastUpdatedAt(OffsetDateTime.now());
        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.save(gitHubLink));

        assertEquals(gitHubLink.getId(), savedGitHubLink.getId());
        assertEquals(gitHubLink.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertEquals(gitHubLink.getForksCount(), savedGitHubLink.getForksCount());
    }

    @Test
    @Transactional
    @Rollback
    public void assertThatUpdateGitHubLinkWorksRight() {
        final GitHubLink gitHubLink = new GitHubLink()
            .setDefaultBranch("master")
            .setForksCount(2L);
        gitHubLink.setId(4515L);
        gitHubLink.setUrl(URI.create("https://link.ru"));
        gitHubLink.setLastUpdatedAt(OffsetDateTime.now());

        GitHubLink savedGitHubLink = Objects.requireNonNull(gitHubLinkRepository.save(gitHubLink));

        savedGitHubLink.setForksCount(3L);
        gitHubLinkRepository.save(savedGitHubLink);

        assertEquals(gitHubLink.getId(), savedGitHubLink.getId());
        assertEquals(gitHubLink.getDefaultBranch(), savedGitHubLink.getDefaultBranch());
        assertNotEquals(gitHubLink.getForksCount(), savedGitHubLink.getForksCount());
    }
}
