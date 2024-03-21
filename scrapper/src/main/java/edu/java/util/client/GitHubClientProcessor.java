package edu.java.util.client;

import edu.java.entity.Link;
import edu.java.service.client.GitHubClient;
import java.net.URI;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GitHubClientProcessor extends BaseClientProcessor {
    private static final Pattern GIT_HUB_PATH_PATTERN =
        Pattern.compile("^/(?<userName>[a-z-A-Z0-9]+)/(?<repositoryName>[\\w-.]+)(/)?$");
    private final GitHubClient gitHubClient;

    public GitHubClientProcessor(GitHubClient gitHubClient) {
        super("github.com");

        this.gitHubClient = gitHubClient;
    }

    @Override
    public boolean isCandidate(URI url) {
        return host.equals(url.getHost().toLowerCase())
            && GIT_HUB_PATH_PATTERN.matcher(url.getPath()).matches();
    }

    @Override
    public Mono<String> getUpdate(Link link) {
        return gitHubClient.getUserRepository(link.getUrl().getPath())
            .mapNotNull(response -> {
                if (response.updatedAt().isAfter(link.getLastUpdatedAt())) {
                    return "Репозиторий обновлён";
                }

                return null;
            });
    }
}
