package edu.java.repository.jooq;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import edu.java.repository.SpecificLinkRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink.GITHUB_LINK;

@Repository
@RequiredArgsConstructor
public class JooqGitHubLinkRepository implements SpecificLinkRepository<GitHubLink> {
    private final DSLContext dslContext;

    @Override
    public GitHubLink getLink(Link link) {
        return dslContext.select(GITHUB_LINK.fields())
            .from(GITHUB_LINK)
            .where(GITHUB_LINK.ID.eq(link.getId()))
            .fetchOneInto(GitHubLink.class);
    }

    @Override
    public GitHubLink addLink(GitHubLink link) {
        return dslContext.insertInto(GITHUB_LINK, GITHUB_LINK.ID, GITHUB_LINK.DEFAULT_BRANCH, GITHUB_LINK.FORKS_COUNT)
            .values(link.getId(), link.getDefaultBranch(), link.getForksCount())
            .returning(GITHUB_LINK.fields())
            .fetchOneInto(GitHubLink.class);
    }

    @Override
    public void updateLink(GitHubLink link) {
        dslContext.update(GITHUB_LINK)
            .set(GITHUB_LINK.DEFAULT_BRANCH, link.getDefaultBranch())
            .set(GITHUB_LINK.FORKS_COUNT, link.getForksCount())
            .where(GITHUB_LINK.ID.eq(link.getId()))
            .execute();
    }
}
