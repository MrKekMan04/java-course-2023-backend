package edu.java.util.client;

import edu.java.entity.Link;
import edu.java.entity.dto.StackOverflowResponseDTO;
import edu.java.service.client.StackOverflowClient;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowClientProcessor extends BaseClientProcessor {
    private static final Pattern STACK_OVERFLOW_PATH_PATTERN =
        Pattern.compile("^/questions/(?<questionId>\\d+)(/[\\w-]*)?(/)?$");
    private final StackOverflowClient stackOverflowClient;

    public StackOverflowClientProcessor(StackOverflowClient stackOverflowClient) {
        super("stackoverflow.com");

        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public boolean isCandidate(URI url) {
        return host.equals(url.getHost())
            && STACK_OVERFLOW_PATH_PATTERN.matcher(url.getPath()).matches();
    }

    @Override
    public Mono<String> getUpdate(Link link) {
        Matcher matcher = STACK_OVERFLOW_PATH_PATTERN.matcher(link.getUrl().getPath());

        if (matcher.matches()) {
            return stackOverflowClient.getQuestionsInfo(matcher.group("questionId"))
                .mapNotNull(response -> {
                    StackOverflowResponseDTO.Question first = response.items().getFirst();
                    if (first.lastActivityDate().isAfter(link.getLastUpdatedAt())) {
                        return "Вопрос обновлён";
                    }

                    return null;
                });
        }

        return Mono.empty();
    }
}
