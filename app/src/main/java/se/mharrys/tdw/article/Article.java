package se.mharrys.tdw.article;

import java.util.Date;
import java.util.List;

import se.mharrys.tdw.author.Author;

/**
 * Describes an website article.
 */
public interface Article {

    /**
     * Return id.
     */
    int getId();

    /**
     * Return published date.
     */
    Date getPublishedDate();

    /**
     * Return authors.
     */
    List<Author> getAuthors();

    /**
     * Return title.
     */
    String getTitle();

    /**
     * Return body.
     */
    String getBody();
}