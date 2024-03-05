package edu.java.bot.command;

import edu.java.bot.entity.dto.ApiErrorResponse;
import edu.java.bot.entity.dto.LinkResponse;
import edu.java.bot.exception.ApiErrorResponseException;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnTrackCommandTest extends CommandTest {
    private Command unTrackCommand;

    @Override
    public void setUp() {
        super.setUp();

        unTrackCommand = new UnTrackCommand(client);
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/untrack", unTrackCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Прекратить отслеживание ссылки", unTrackCommand.description());
    }

    @Test
    public void assertThatWrongSyntaxReturnedRightResponse() {
        Mockito.doReturn("/untrack").when(message).text();

        assertEquals("Неверный синтаксис команды", unTrackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatIncorrectLinkReturnedRightResponse() {
        Mockito.doReturn("/untrack link").when(message).text();

        assertEquals("Ссылка некорректна", unTrackCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatUnTrackExistingLinkReturnedRightResponse() {
        Mockito.doReturn("/untrack https://github.com").when(message).text();
        URI uri = Mockito.spy(URI.create("https://github.com"));

        Mockito.doReturn(Mono.just(ResponseEntity.ok().body(new LinkResponse(0L, uri))))
            .when(client).removeLink(Mockito.any(), Mockito.any());

        assertEquals(
            "[Ссылка](%s) успешно удалена из отслеживаемых".formatted(uri),
            unTrackCommand.handle(update).getParameters().get("text")
        );
    }

    @Test
    public void assertThatUnTrackNotExistingLinkReturnedRightResponse() {
        Mockito.doReturn("/untrack https://github.com").when(message).text();
        ApiErrorResponse mock = Mockito.mock(ApiErrorResponse.class);
        Mockito.doReturn("Ссылка не отслеживается").when(mock).description();
        Mockito.doReturn(Mono.error(new ApiErrorResponseException(mock)))
            .when(client).removeLink(Mockito.any(), Mockito.any());

        assertEquals("Ссылка не отслеживается", unTrackCommand.handle(update).getParameters().get("text"));
    }
}
