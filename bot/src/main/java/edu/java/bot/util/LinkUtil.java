package edu.java.bot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkUtil {
    private static final Pattern LINK_PATTERN =
        Pattern.compile("^https://([^.]+\\.)*(?<secondLevelDomain>[^. ]+)\\.(?<firstLevelDomain>[^. ]+)(/([^. ]+)?)?");

    private LinkUtil() {
    }

    public static boolean isLinkCorrect(String link) {
        return LINK_PATTERN.matcher(link).matches();
    }

    public static String getSecondLevelDomain(String link) {
        Matcher linkMatcher = LINK_PATTERN.matcher(link);

        if (linkMatcher.matches()) {
            return linkMatcher.group("secondLevelDomain");
        }

        return null;
    }
}
