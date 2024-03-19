package edu.java.service.jooq;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import edu.java.repository.jooq.JooqGitHubLinkRepository;
import edu.java.service.GitHubLinkService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqGitHubLinkService implements GitHubLinkService {
    private final JooqGitHubLinkRepository jooqGitHubLinkRepository;

    @Override
    public Optional<GitHubLink> getLink(Link link) {
        GitHubLink gitHubLink = jooqGitHubLinkRepository.getLink(link);

        return gitHubLink != null ? Optional.of(gitHubLink) : Optional.empty();
    }

    @Override
    public GitHubLink addLink(GitHubLink link) {
        return jooqGitHubLinkRepository.addLink(link);
    }

    @Override
    public void updateLink(GitHubLink link) {
        jooqGitHubLinkRepository.updateLink(link);
    }
}
