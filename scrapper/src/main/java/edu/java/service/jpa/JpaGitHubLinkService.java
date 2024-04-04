package edu.java.service.jpa;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import edu.java.repository.jpa.JpaGitHubLinkRepository;
import edu.java.service.GitHubLinkService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaGitHubLinkService implements GitHubLinkService {
    private final JpaGitHubLinkRepository jpaGitHubLinkRepository;

    @Override
    public Optional<GitHubLink> getLink(Link link) {
        return jpaGitHubLinkRepository.findById(link.getId());
    }

    @Override
    public GitHubLink addLink(GitHubLink link) {
        jpaGitHubLinkRepository.add(link.getId(), link.getDefaultBranch(), link.getForksCount());
        return link;
    }

    @Override
    public void updateLink(GitHubLink link) {
        jpaGitHubLinkRepository.save(link);
    }
}
