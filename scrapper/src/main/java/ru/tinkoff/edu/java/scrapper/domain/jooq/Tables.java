/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;

import javax.annotation.processing.Generated;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Assignment;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.TelegramChat;


/**
 * Convenience access to all tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Tables {

    /**
     * The table <code>ASSIGNMENT</code>.
     */
    public static final Assignment ASSIGNMENT = Assignment.ASSIGNMENT;

    /**
     * The table <code>GITHUB_LINK</code>.
     */
    public static final GithubLink GITHUB_LINK = GithubLink.GITHUB_LINK;

    /**
     * The table <code>LINK</code>.
     */
    public static final Link LINK = Link.LINK;

    /**
     * The table <code>STACKOVERFLOW_LINK</code>.
     */
    public static final StackoverflowLink STACKOVERFLOW_LINK = StackoverflowLink.STACKOVERFLOW_LINK;

    /**
     * The table <code>TELEGRAM_CHAT</code>.
     */
    public static final TelegramChat TELEGRAM_CHAT = TelegramChat.TELEGRAM_CHAT;
}
