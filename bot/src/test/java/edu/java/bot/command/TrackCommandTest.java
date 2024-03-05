package edu.java.bot.command;

import edu.java.bot.entity.dto.AddLinkRequest;
import edu.java.bot.entity.dto.ApiErrorResponse;
import edu.java.bot.entity.dto.LinkResponse;
import edu.java.bot.exception.ApiErrorResponseException;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackCommandTest extends CommandTest {
    private Command trackCommand;

    @Override
    public void setUp() {
        super.setUp();

        trackCommand = new TrackCommand(client);
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/track", trackCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Начать отслеживание ссылки", trackCommand.description());
    }

    @Test
    public void assertThatWrongSyntaxReturnedRightResponse() {
        Mockito.doReturn("/track").when(message).text();

        assertEquals("Неверный синтаксис команды", trackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatIncorrectLinkReturnedRightResponse() {
        Mockito.doReturn("/track link").when(message).text();

        assertEquals("Ссылка некорректна", trackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatAlreadyAddedLinkReturnedRightResponse() {
        Mockito.doReturn("/track https://github.com").when(message).text();

        ApiErrorResponse mock = Mockito.mock(ApiErrorResponse.class);
        Mockito.doReturn("Ссылка уже отслеживается").when(mock).description();
        Mockito.doReturn(Mono.error(new ApiErrorResponseException(mock)))
            .when(client).addLink(Mockito.any(), Mockito.any());

        assertEquals("Ссылка уже отслеживается", trackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatAddUniqueLinkReturnedRightResponse() {
        Mockito.doReturn("/track https://github.com").when(message).text();

        AddLinkRequest request = Mockito.spy(new AddLinkRequest(URI.create("https://github.com")));
        Mockito.doReturn(Mono.just(ResponseEntity.ok().body(new LinkResponse(0L, request.link()))))
            .when(client).addLink(Mockito.any(), Mockito.any());

        assertEquals(
            "[Ссылка](%s) успешно добавлена в отслеживаемые".formatted(request.link()),
            trackCommand.handle(update).getParameters().get("text")
        );
    }
}
