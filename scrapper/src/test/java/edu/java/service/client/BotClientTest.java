package edu.java.service.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.entity.dto.LinkUpdateRequest;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BotClientTest {
    @RegisterExtension
    public static final WireMockExtension WIRE_MOCK_SERVER = WireMockExtension.newInstance()
        .options(WireMockConfiguration.wireMockConfig().dynamicPort())
        .build();
    private static final String LINK_UPDATES_URI = "/updates";

    @DynamicPropertySource
    public static void configureRegistry(DynamicPropertyRegistry registry) {
        registry.add("app.api-link.bot", WIRE_MOCK_SERVER::baseUrl);
    }

    @Autowired
    private BotClient client;

    @Test
    public void assertThatSendUpdateReturnedStatusOk() {
        final LinkUpdateRequest request =
            new LinkUpdateRequest(0L, URI.create("https://github.com"), "Update", List.of(0L));
        WIRE_MOCK_SERVER.stubFor(WireMock.post(LINK_UPDATES_URI)
            .withRequestBody(WireMock.equalToJson("""
                {
                    "id": %d,
                    "url": "%s",
                      "description": "%s",
                      "tgChatIds": [
                        %d
                      ]
                }
                """.formatted(request.id(), request.url(), request.description(), request.tgChatIds().getFirst())))
            .willReturn(WireMock.ok()));

        ResponseEntity<Void> response = client.sendUpdate(request).block();

        Objects.requireNonNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
