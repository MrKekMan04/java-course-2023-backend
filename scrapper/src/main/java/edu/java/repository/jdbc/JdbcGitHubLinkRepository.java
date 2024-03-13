package edu.java.repository.jdbc;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcGitHubLinkRepository implements JdbcSpecificLinkRepository<GitHubLink> {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_LINK_BY_ID = "SELECT * FROM github_link WHERE id=?";
    private static final String UPDATE_LINK = "UPDATE github_link SET default_branch=?, forks_count=? WHERE id=?";
    private static final String ADD_LINK =
        "INSERT INTO github_link (id, default_branch, forks_count) VALUES (?,?,?) RETURNING *";

    @Override
    @Transactional
    public GitHubLink getLink(Link link) {
        return jdbcTemplate.queryForObject(
            SELECT_LINK_BY_ID,
            new BeanPropertyRowMapper<>(GitHubLink.class),
            link.getId()
        );
    }

    @Override
    @Transactional
    public GitHubLink addLink(GitHubLink link) {
        return jdbcTemplate.queryForObject(
            ADD_LINK,
            new BeanPropertyRowMapper<>(GitHubLink.class),
            link.getId(),
            link.getDefaultBranch(),
            link.getForksCount()
        );
    }

    @Override
    @Transactional
    public void updateLink(GitHubLink link) {
        jdbcTemplate.update(UPDATE_LINK, link.getDefaultBranch(), link.getForksCount(), link.getId());
    }
}
