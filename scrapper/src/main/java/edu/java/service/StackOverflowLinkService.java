package edu.java.service;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface StackOverflowLinkService {
    @Transactional(readOnly = true)
    Optional<StackOverflowLink> getLink(Link link);

    @Transactional
    StackOverflowLink addLink(StackOverflowLink link);

    @Transactional
    void updateLink(StackOverflowLink link);
}
