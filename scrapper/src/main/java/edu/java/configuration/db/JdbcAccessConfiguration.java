package edu.java.configuration.db;

import edu.java.repository.jdbc.JdbcGitHubLinkRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcStackOverflowLinkRepository;
import edu.java.repository.jdbc.JdbcTelegramChatRepository;
import edu.java.service.GitHubLinkService;
import edu.java.service.LinkService;
import edu.java.service.StackOverflowLinkService;
import edu.java.service.TelegramChatService;
import edu.java.service.jdbc.JdbcGitHubLinkService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcStackOverflowLinkService;
import edu.java.service.jdbc.JdbcTelegramChatService;
import edu.java.util.LinkUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcLinkRepository jdbcLinkRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public LinkService linkService(JdbcLinkRepository jdbcLinkRepository, LinkUtil linkUtil) {
        return new JdbcLinkService(jdbcLinkRepository, linkUtil);
    }

    @Bean
    public JdbcTelegramChatRepository jdbcTelegramChatRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcTelegramChatRepository(jdbcTemplate);
    }

    @Bean
    public TelegramChatService telegramChatService(JdbcTelegramChatRepository jdbcTelegramChatRepository) {
        return new JdbcTelegramChatService(jdbcTelegramChatRepository);
    }

    @Bean
    public JdbcGitHubLinkRepository jdbcGitHubLinkRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcGitHubLinkRepository(jdbcTemplate);
    }

    @Bean
    public GitHubLinkService gitHubLinkService(JdbcGitHubLinkRepository jdbcGitHubLinkRepository) {
        return new JdbcGitHubLinkService(jdbcGitHubLinkRepository);
    }

    @Bean
    public JdbcStackOverflowLinkRepository jdbcStackOverflowLinkRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcStackOverflowLinkRepository(jdbcTemplate);
    }

    @Bean
    public StackOverflowLinkService stackOverflowLinkService(
        JdbcStackOverflowLinkRepository jdbcStackOverflowLinkRepository
    ) {
        return new JdbcStackOverflowLinkService(jdbcStackOverflowLinkRepository);
    }
}
