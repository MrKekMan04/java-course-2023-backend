package edu.java.repository.jdbc;

import edu.java.entity.Link;

public interface JdbcSpecificLinkRepository<L extends Link> {
    L getLink(Link link);

    L addLink(L link);

    void updateLink(L link);
}
