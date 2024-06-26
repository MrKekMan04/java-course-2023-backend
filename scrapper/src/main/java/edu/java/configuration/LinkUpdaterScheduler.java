package edu.java.configuration;

import edu.java.entity.dto.LinkUpdateRequest;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdateService;
import edu.java.util.client.BaseClientProcessor;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final List<BaseClientProcessor> clientProcessors;
    private final LinkService linkService;
    private final LinkUpdateService linkUpdateService;
    private final ApplicationConfig config;

    @Scheduled(fixedDelayString = "PT${app.scheduler.interval}")
    public void update() {
        linkService.listAllWithInterval(config.scheduler().linkLastCheckInterval()).forEach(link -> {
            for (BaseClientProcessor clientProcessor : clientProcessors) {
                if (clientProcessor.isCandidate(link.getUrl())) {
                    clientProcessor.getUpdate(link)
                        .filter(Objects::nonNull)
                        .publishOn(Schedulers.boundedElastic())
                        .map(update -> new LinkUpdateRequest(
                            link.getId(),
                            link.getUrl(),
                            update,
                            linkService.getAllChatsForLink(link.getId())
                        ))
                        .subscribe(linkUpdateService::sendUpdate);
                    linkService.updateLink(link.setLastUpdatedAt(OffsetDateTime.now()));
                    break;
                }
            }
        });
    }
}
