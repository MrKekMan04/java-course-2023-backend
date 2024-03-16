package edu.java.configuration;

import edu.java.entity.dto.LinkUpdateRequest;
import edu.java.service.LinkService;
import edu.java.service.client.BotClient;
import edu.java.util.client.BaseClientProcessor;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final List<BaseClientProcessor> clientProcessors;
    private final LinkService jooqLinkService;
    private final BotClient botClient;
    private final ApplicationConfig config;

    @Scheduled(fixedDelayString = "PT${app.scheduler.interval}")
    public void update() {
        jooqLinkService.listAllWithInterval(config.scheduler().linkLastCheckInterval()).forEach(link -> {
            for (BaseClientProcessor clientProcessor : clientProcessors) {
                if (clientProcessor.isCandidate(link.getUrl())) {
                    clientProcessor.getUpdate(link)
                        .filter(Objects::nonNull)
                        .map(update -> new LinkUpdateRequest(
                            link.getId(),
                            link.getUrl(),
                            update,
                            jooqLinkService.getAllChatsForLink(link.getId())
                        ))
                        .flatMap(botClient::sendUpdate)
                        .subscribe();

                    jooqLinkService.updateLink(link.setLastUpdatedAt(OffsetDateTime.now()));
                    break;
                }
            }
        });
    }
}
