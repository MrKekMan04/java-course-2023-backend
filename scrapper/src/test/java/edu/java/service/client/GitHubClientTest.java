package edu.java.service.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.entity.dto.GitHubResponseDTO;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubClientTest {
    @RegisterExtension
    private static final WireMockExtension WIRE_MOCK_SERVER = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig().dynamicPort())
        .build();

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.client.git-hub", WIRE_MOCK_SERVER::baseUrl);
    }

    @Autowired
    private GitHubClient client;

    @Test
    public void assertThatExistsRepositoryGetUserRepositoryReturnedOk() {
        final String repositoryPath = "username/repository";
        final OffsetDateTime repositoryUpdatedAt = OffsetDateTime.of(2011, 1, 26, 19, 14, 43, 0, ZoneOffset.of("Z"));

        WIRE_MOCK_SERVER.stubFor(WireMock.get("/repos/" + repositoryPath)
            .willReturn(WireMock.ok()
                .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                        "full_name": "%s",
                        "updated_at": "2011-01-26T19:14:43Z"
                    }
                    """.formatted(repositoryPath))));

        GitHubResponseDTO response = client.getUserRepository(repositoryPath).block();

        Objects.requireNonNull(response);
        assertEquals(repositoryPath, response.fullName());
        assertEquals(repositoryUpdatedAt, response.updatedAt());
    }

    @Test
    public void assertThatNonExistsOrPrivateRepositoryGetUserRepositoryReturnedNotFound() {
        final String repositoryPath = "username/nonExists";

        WIRE_MOCK_SERVER.stubFor(WireMock.get("/repos/" + repositoryPath)
            .willReturn(WireMock.notFound()
                .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                        "message": "Not Found",
                        "documentation_url": "https://docs.github.com/rest/repos/repos#get-a-repository"
                    }
                    """)));

        assertThrows(WebClientResponseException.class, () -> client.getUserRepository(repositoryPath).block());
    }
}
