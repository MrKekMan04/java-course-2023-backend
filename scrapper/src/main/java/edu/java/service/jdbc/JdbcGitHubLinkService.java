package edu.java.service.jdbc;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import edu.java.repository.jdbc.JdbcGitHubLinkRepository;
import edu.java.service.GitHubLinkService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcGitHubLinkService implements GitHubLinkService {
    private final JdbcGitHubLinkRepository jdbcGitHubLinkRepository;

    @Override
    public Optional<GitHubLink> getLink(Link link) {
        try {
            return Optional.of(jdbcGitHubLinkRepository.getLink(link));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public GitHubLink addLink(GitHubLink link) {
        return jdbcGitHubLinkRepository.addLink(link);
    }

    @Override
    public void updateLink(GitHubLink link) {
        jdbcGitHubLinkRepository.updateLink(link);
    }
}
