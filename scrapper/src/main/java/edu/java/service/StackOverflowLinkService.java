package edu.java.service;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import java.util.Optional;

public interface StackOverflowLinkService {
    Optional<StackOverflowLink> getLink(Link link);

    StackOverflowLink addLink(StackOverflowLink link);

    void updateLink(StackOverflowLink link);
}
