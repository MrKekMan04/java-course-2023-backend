package edu.java.repository.jdbc;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import edu.java.repository.SpecificLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class JdbcStackOverflowLinkRepository implements SpecificLinkRepository<StackOverflowLink> {
    private static final String SELECT_LINK_BY_ID = "SELECT * FROM stackoverflow_link WHERE id=?";
    private static final String UPDATE_LINK = "UPDATE stackoverflow_link SET answer_count=?, score=? WHERE id=?";
    private static final String ADD_LINK =
        "INSERT INTO stackoverflow_link (id, answer_count, score) VALUES (?,?,?) RETURNING *";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public StackOverflowLink getLink(Link link) {
        return jdbcTemplate.queryForObject(
            SELECT_LINK_BY_ID,
            new BeanPropertyRowMapper<>(StackOverflowLink.class),
            link.getId()
        );
    }

    @Override
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
    public void updateLink(StackOverflowLink link) {
        jdbcTemplate.update(UPDATE_LINK, link.getAnswerCount(), link.getScore(), link.getId());
    }
}
