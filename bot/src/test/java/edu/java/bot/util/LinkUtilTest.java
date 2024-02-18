package edu.java.bot.util;

import java.net.URI;
import org.junit.jupiter.api.Test;
import static edu.java.bot.util.LinkUtil.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinkUtilTest {
    private static final String SIMPLE_LINK = "https://github.com";
    private static final String SIMPLE_LINK_WITH_PATH = "https://github.com/MrKekMan04";
    private static final String SIMPLE_LINK_WITH_EMPTY_PATH = "https://github.com/";
    private static final String SIMPLE_LINK_WITH_THIRD_LEVEL_DOMAIN = "https://gist.github.com/";
    private static final String INCORRECT_LINK = "https:///git hub.com";

    @Test
    public void assertThatParseReturnedRight() {
        URI hostSimpleLink = parse(SIMPLE_LINK);
        assertNotNull(hostSimpleLink);
        assertEquals("github.com", hostSimpleLink.getHost());
        URI hostSimpleLinkWithPath = parse(SIMPLE_LINK_WITH_PATH);
        assertNotNull(hostSimpleLinkWithPath);
        assertEquals("github.com", hostSimpleLinkWithPath.getHost());
        URI host1 = parse(SIMPLE_LINK_WITH_EMPTY_PATH);
        assertNotNull(host1);
        assertEquals("github.com", host1.getHost());
        URI hostSimpleLinkWithThirdLevelDomain = parse(SIMPLE_LINK_WITH_THIRD_LEVEL_DOMAIN);
        assertNotNull(hostSimpleLinkWithThirdLevelDomain);
        assertEquals("gist.github.com", hostSimpleLinkWithThirdLevelDomain.getHost());
    }

    @Test
    public void assertThatIncorrectLinkParseReturnedNull() {
        assertNull(parse(INCORRECT_LINK));
    }
}
