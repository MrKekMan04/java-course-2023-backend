package edu.java.bot.util;

import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LinkUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    private LinkUtil() {
    }

    public static URI parse(String link) {
        try {
            URI uri = URI.create(link);
            return uri.getHost() != null && uri.getPath() != null ? uri : null;
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            return null;
        }
    }
}
