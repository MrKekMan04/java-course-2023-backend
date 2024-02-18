package edu.java.service.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.entity.dto.StackOverflowResponseDTO;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StackOverflowClientTest {
    @RegisterExtension
    private static final WireMockExtension WIRE_MOCK_SERVER = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig().dynamicPort())
        .build();

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("app.client.stack-overflow", WIRE_MOCK_SERVER::baseUrl);
    }

    @Autowired
    private StackOverflowClient client;

    @Test
    public void assertThatExistsQuestionReturnedOk() {
        final String ids = "123";
        int epochSecond = 1590400952;
        final OffsetDateTime lastActivityDate =
            OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneId.of("Z"));
        final String title = "Java lib or app to convert CSV to XML file?";

        WIRE_MOCK_SERVER.stubFor(WireMock.get("/questions/" + ids + "?site=stackoverflow")
            .willReturn(WireMock.ok()
                .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                        "items": [
                            {
                                "last_activity_date": %d,
                                "title": "%s"
                            }
                        ]
                    }
                    """.formatted(epochSecond, title))));

        StackOverflowResponseDTO response = client.getQuestionsInfo(ids).block();

        Objects.requireNonNull(response);
        assertEquals(title, response.items().getFirst().title());
        assertEquals(lastActivityDate, response.items().getFirst().lastActivityDate());
    }

    @Test
    public void assertThatNonExistsQuestionReturnedOkAndEmptyList() {
        final String ids = "12";

        WIRE_MOCK_SERVER.stubFor(WireMock.get("/questions/" + ids + "?site=stackoverflow")
            .willReturn(WireMock.ok()
                .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("""
                    {
                        "items": []
                    }
                    """)));

        StackOverflowResponseDTO response = client.getQuestionsInfo(ids).block();

        Objects.requireNonNull(response);
        assertTrue(response.items().isEmpty());
    }
}
