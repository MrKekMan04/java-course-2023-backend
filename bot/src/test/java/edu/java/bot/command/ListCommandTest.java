package edu.java.bot.command;

import edu.java.bot.entity.dto.LinkResponse;
import edu.java.bot.entity.dto.ListLinksResponse;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest extends CommandTest {
    private Command listCommand;

    @Override
    public void setUp() {
        super.setUp();

        listCommand = new ListCommand(client);
    }

    @Test
    public void assertThatCommandReturnedRightString() {
        assertEquals("/list", listCommand.command());
    }

    @Test
    public void assertThatDescriptionReturnedRightString() {
        assertEquals("Показать список отслеживаемых ссылок", listCommand.description());
    }

    @Test
    public void assertThatEmptyListOfTrackingLinksHandleReturnedRightString() {
        final String expected = "Вы не отслеживаете ни одной ссылки";

        Mockito.doReturn(Mono.just(ResponseEntity.ok().body(new ListLinksResponse(List.of(), 0))))
            .when(client).getAllLinksForChat(chatId);

        assertEquals(expected, listCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void assertThatNotEmptyListOfTrackingLinksHandleReturnedRightString() {
        final String expected = "Отслеживаемые ссылки:\n1: http://localhost:8080\n";

        Mockito.doReturn(Mono.just(ResponseEntity.ok()
                .body(new ListLinksResponse(
                    List.of(new LinkResponse(0L, URI.create("http://localhost:8080"))),
                    1
                ))))
            .when(client).getAllLinksForChat(chatId);

        assertEquals(
            expected,
            listCommand.handle(update).getParameters().get("text")
        );
    }
}
