package se.mharrys.tdw.article;

import java.util.ArrayList;
import java.util.Date;

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
    ArrayList<Author> getAuthors();

    /**
     * Return title.
     */
    String getTitle();

    /**
     * Return body.
     */
    String getBody();
}