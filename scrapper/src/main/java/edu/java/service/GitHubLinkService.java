package edu.java.service;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import java.util.Optional;

public interface GitHubLinkService {
    Optional<GitHubLink> getLink(Link link);

    GitHubLink addLink(GitHubLink link);

    void updateLink(GitHubLink link);
}
