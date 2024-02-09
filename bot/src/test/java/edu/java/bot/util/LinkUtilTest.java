package edu.java.bot.util;

import org.junit.jupiter.api.Test;
import static edu.java.bot.util.LinkUtil.getSecondLevelDomain;
import static edu.java.bot.util.LinkUtil.isLinkCorrect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkUtilTest {
    private static final String SIMPLE_LINK = "https://github.com";
    private static final String SIMPLE_LINK_WITH_PATH = "https://github.com/MrKekMan04";
    private static final String SIMPLE_LINK_WITH_EMPTY_PATH = "https://github.com/";
    private static final String SIMPLE_LINK_WITH_THIRD_LEVEL_DOMAIN = "https://gist.github.com/";
    private static final String UNSECURED_CONNECTION_LINK = "http://github.com";
    private static final String INCORRECT_LINK = "https://git hub.com";
    private static final String INCORRECT_DOMAIN = "https://.github.com";
    private static final String LINK_WITHOUT_SECOND_LEVEL_DOMAIN = "https://.com";

    @Test
    public void assertThatGetSecondLevelDomainReturnedRight() {
        assertEquals("github", getSecondLevelDomain(SIMPLE_LINK));
        assertEquals("github", getSecondLevelDomain(SIMPLE_LINK_WITH_PATH));
        assertEquals("github", getSecondLevelDomain(SIMPLE_LINK_WITH_EMPTY_PATH));
        assertEquals("github", getSecondLevelDomain(SIMPLE_LINK_WITH_THIRD_LEVEL_DOMAIN));
    }

    @Test
    public void assertThatIncorrectLinkGetSecondLevelDomainReturnedNull() {
        assertNull(getSecondLevelDomain(UNSECURED_CONNECTION_LINK));
        assertNull(getSecondLevelDomain(INCORRECT_LINK));
        assertNull(getSecondLevelDomain(INCORRECT_DOMAIN));
        assertNull(getSecondLevelDomain(LINK_WITHOUT_SECOND_LEVEL_DOMAIN));
    }

    @Test
    public void assertThatIsCorrectLinkWorksRight() {
        assertTrue(isLinkCorrect(SIMPLE_LINK));
        assertTrue(isLinkCorrect(SIMPLE_LINK_WITH_PATH));
        assertTrue(isLinkCorrect(SIMPLE_LINK_WITH_EMPTY_PATH));
        assertTrue(isLinkCorrect(SIMPLE_LINK_WITH_THIRD_LEVEL_DOMAIN));
        assertFalse(isLinkCorrect(UNSECURED_CONNECTION_LINK));
        assertFalse(isLinkCorrect(INCORRECT_LINK));
        assertFalse(isLinkCorrect(INCORRECT_DOMAIN));
        assertFalse(isLinkCorrect(LINK_WITHOUT_SECOND_LEVEL_DOMAIN));
    }
}
