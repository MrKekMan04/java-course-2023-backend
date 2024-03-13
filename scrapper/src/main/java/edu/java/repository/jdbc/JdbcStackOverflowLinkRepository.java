package edu.java.repository.jdbc;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcStackOverflowLinkRepository implements JdbcSpecificLinkRepository<StackOverflowLink> {
    private static final String SELECT_LINK_BY_ID = "SELECT * FROM stackoverflow_link WHERE id=?";
    private static final String UPDATE_LINK = "UPDATE stackoverflow_link SET answer_count=?, score=? WHERE id=?";
    private static final String ADD_LINK =
        "INSERT INTO stackoverflow_link (id, answer_count, score) VALUES (?,?,?) RETURNING *";

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public StackOverflowLink getLink(Link link) {
        return jdbcTemplate.queryForObject(
            SELECT_LINK_BY_ID,
            new BeanPropertyRowMapper<>(StackOverflowLink.class),
            link.getId()
        );
    }

    @Override
    @Transactional
    public StackOverflowLink addLink(StackOverflowLink link) {
        return jdbcTemplate.queryForObject(
            ADD_LINK,
            new BeanPropertyRowMapper<>(StackOverflowLink.class),
            link.getId(),
            link.getAnswerCount(),
            link.getScore()
        );
    }

    @Override
    @Transactional
    public void updateLink(StackOverflowLink link) {
        jdbcTemplate.update(UPDATE_LINK, link.getAnswerCount(), link.getScore(), link.getId());
    }
}
