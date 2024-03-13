package edu.java.util.client;

import edu.java.entity.GitHubLink;
import edu.java.entity.Link;
import edu.java.service.GitHubLinkService;
import edu.java.service.client.GitHubClient;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GitHubClientProcessor extends BaseClientProcessor {
    private static final Pattern GIT_HUB_PATH_PATTERN =
        Pattern.compile("^/(?<userName>[a-z-A-Z0-9]+)/(?<repositoryName>[\\w-.]+)(/)?$");
    private final GitHubClient gitHubClient;
    private final GitHubLinkService gitHubLinkService;

    public GitHubClientProcessor(GitHubClient gitHubClient, GitHubLinkService gitHubLinkService) {
        super("github.com");

        this.gitHubClient = gitHubClient;
        this.gitHubLinkService = gitHubLinkService;
    }

    @Override
    public boolean isCandidate(URI url) {
        return host.equals(url.getHost().toLowerCase())
            && GIT_HUB_PATH_PATTERN.matcher(url.getPath()).matches();
    }

    @Override
    public Mono<String> getUpdate(Link link) {
        Optional<GitHubLink> specificInfo = gitHubLinkService.getLink(link);

        return gitHubClient.getUserRepository(link.getUrl().getPath())
            .mapNotNull(response -> {
                StringBuilder update = new StringBuilder();

                if (response.updatedAt().isAfter(link.getLastUpdatedAt())) {
                    update.append("Репозиторий обновлён\n");
                }

                boolean isDirty = false;
                GitHubLink gitHubLink = getEntity(specificInfo, link);

                if (specificInfo.isEmpty() || !gitHubLink.getDefaultBranch().equals(response.defaultBranch())) {
                    isDirty = true;
                    if (specificInfo.isPresent()) {
                        update.append("Основная ветка поменяна: %s -> %s\n"
                            .formatted(gitHubLink.getDefaultBranch(), response.defaultBranch()));
                    }
                    gitHubLink.setDefaultBranch(response.defaultBranch());
                }

                if (specificInfo.isEmpty() || !gitHubLink.getForksCount().equals(response.forksCount())) {
                    isDirty = true;
                    if (specificInfo.isPresent()) {
                        update.append("Количество форков изменилось: %d -> %d\n"
                            .formatted(gitHubLink.getForksCount(), response.forksCount()));
                    }
                    gitHubLink.setForksCount(response.forksCount());
                }

                if (isDirty) {
                    updateEntity(gitHubLink, specificInfo.isEmpty());
                }

                return update.isEmpty() ? null : update.toString();
            });
    }

    private GitHubLink getEntity(Optional<GitHubLink> repositoryLink, Link link) {
        if (repositoryLink.isPresent()) {
            return repositoryLink.get();
        }

        GitHubLink gitHubLink = new GitHubLink();
        gitHubLink.setId(link.getId());

        return gitHubLink;
    }

    private void updateEntity(GitHubLink gitHubLink, boolean isNewEntity) {
        if (isNewEntity) {
            gitHubLinkService.addLink(gitHubLink);
        } else {
            gitHubLinkService.updateLink(gitHubLink);
        }
    }
}
