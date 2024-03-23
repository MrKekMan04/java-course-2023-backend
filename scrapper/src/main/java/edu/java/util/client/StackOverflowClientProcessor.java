package edu.java.util.client;

import edu.java.entity.Link;
import edu.java.entity.StackOverflowLink;
import edu.java.service.StackOverflowLinkService;
import edu.java.service.client.StackOverflowClient;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowClientProcessor extends BaseClientProcessor {
    private static final Pattern STACK_OVERFLOW_PATH_PATTERN =
        Pattern.compile("^/questions/(?<questionId>\\d+)(/[\\w-]*)?(/)?$");
    private final StackOverflowClient stackOverflowClient;
    private final StackOverflowLinkService stackOverflowLinkService;

    public StackOverflowClientProcessor(
        StackOverflowClient stackOverflowClient,
        StackOverflowLinkService jooqStackOverflowLinkService
    ) {
        super("stackoverflow.com");

        this.stackOverflowClient = stackOverflowClient;
        this.stackOverflowLinkService = jooqStackOverflowLinkService;
    }

    @Override
    public boolean isCandidate(URI url) {
        return host.equals(url.getHost())
            && STACK_OVERFLOW_PATH_PATTERN.matcher(url.getPath()).matches();
    }

    @Override
    public Mono<String> getUpdate(Link link) {
        Matcher matcher = STACK_OVERFLOW_PATH_PATTERN.matcher(link.getUrl().getPath());

        if (!matcher.matches()) {
            return Mono.empty();
        }

        Optional<StackOverflowLink> specificInfo = stackOverflowLinkService.getLink(link);

        return stackOverflowClient.getQuestionsInfo(matcher.group("questionId"))
            .map(response -> response.items().getFirst())
            .mapNotNull(response -> {
                StringBuilder update = new StringBuilder();

                if (response.lastActivityDate().isAfter(link.getLastUpdatedAt())) {
                    update.append("Вопрос обновлён\n");
                }

                boolean isDirty = false;
                StackOverflowLink stackOverflowLink = getEntity(specificInfo, link);

                if (specificInfo.isEmpty() || !stackOverflowLink.getAnswerCount().equals(response.answerCount())) {
                    isDirty = true;
                    if (specificInfo.isPresent()) {
                        update.append("Количество ответов изменилось: %d -> %d\n"
                            .formatted(stackOverflowLink.getAnswerCount(), response.answerCount()));
                    }
                    stackOverflowLink.setAnswerCount(response.answerCount());
                }

                if (specificInfo.isEmpty() || !stackOverflowLink.getScore().equals(response.score())) {
                    isDirty = true;
                    if (specificInfo.isPresent()) {
                        update.append("Количество очков изменилось: %d -> %d\n"
                            .formatted(stackOverflowLink.getScore(), response.score()));
                    }
                    stackOverflowLink.setScore(response.score());
                }

                if (isDirty) {
                    updateEntity(stackOverflowLink, specificInfo.isEmpty());
                }

                return update.isEmpty() ? null : update.toString();
            });
    }

    private StackOverflowLink getEntity(Optional<StackOverflowLink> repositoryLink, Link link) {
        if (repositoryLink.isPresent()) {
            return repositoryLink.get();
        }

        StackOverflowLink stackOverflowLink = new StackOverflowLink();
        stackOverflowLink.setId(link.getId());

        return stackOverflowLink;
    }

    private void updateEntity(StackOverflowLink stackOverflowLink, boolean isNewEntity) {
        if (isNewEntity) {
            stackOverflowLinkService.addLink(stackOverflowLink);
        } else {
            stackOverflowLinkService.updateLink(stackOverflowLink);
        }
    }
}
