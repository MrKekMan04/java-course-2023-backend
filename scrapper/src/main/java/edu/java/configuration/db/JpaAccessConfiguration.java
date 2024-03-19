package edu.java.configuration.db;

import edu.java.repository.jpa.JpaGitHubLinkRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaStackOverflowLinkRepository;
import edu.java.repository.jpa.JpaTelegramChatRepository;
import edu.java.service.GitHubLinkService;
import edu.java.service.LinkService;
import edu.java.service.StackOverflowLinkService;
import edu.java.service.TelegramChatService;
import edu.java.service.jpa.JpaGitHubLinkService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaStackOverflowLinkService;
import edu.java.service.jpa.JpaTelegramChatService;
import edu.java.util.LinkUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaLinkRepository jpaLinkRepository,
        JpaTelegramChatRepository jpaTelegramChatRepository,
        LinkUtil linkUtil
    ) {
        return new JpaLinkService(jpaLinkRepository, jpaTelegramChatRepository, linkUtil);
    }

    @Bean
    public TelegramChatService telegramChatService(JpaTelegramChatRepository jpaTelegramChatRepository) {
        return new JpaTelegramChatService(jpaTelegramChatRepository);
    }

    @Bean
    public GitHubLinkService gitHubLinkService(JpaGitHubLinkRepository jpaGitHubLinkRepository) {
        return new JpaGitHubLinkService(jpaGitHubLinkRepository);
    }

    @Bean
    public StackOverflowLinkService stackOverflowLinkService(
        JpaStackOverflowLinkRepository jpaStackOverflowLinkRepository
    ) {
        return new JpaStackOverflowLinkService(jpaStackOverflowLinkRepository);
    }
}
