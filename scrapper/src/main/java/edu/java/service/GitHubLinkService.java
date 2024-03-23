package edu.java.service;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface GitHubLinkService {
    @Transactional(readOnly = true)
    Optional<GitHubLink> getLink(Link link);

    @Transactional
    GitHubLink addLink(GitHubLink link);

    @Transactional
    void updateLink(GitHubLink link);
}
