package edu.java.service.jpa;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import edu.java.repository.jpa.JpaStackOverflowLinkRepository;
import edu.java.service.StackOverflowLinkService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaStackOverflowLinkService implements StackOverflowLinkService {
    private final JpaStackOverflowLinkRepository jpaStackOverflowLinkRepository;

    @Override
    public Optional<StackOverflowLink> getLink(Link link) {
        return jpaStackOverflowLinkRepository.findById(link.getId());
    }

    @Override
    public StackOverflowLink addLink(StackOverflowLink link) {
        jpaStackOverflowLinkRepository.add(link.getId(), link.getAnswerCount(), link.getScore());
        return link;
    }

    @Override
    public void updateLink(StackOverflowLink link) {
        jpaStackOverflowLinkRepository.save(link);
    }
}
