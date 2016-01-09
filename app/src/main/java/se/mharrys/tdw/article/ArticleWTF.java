package se.mharrys.tdw.article;

import java.util.Date;
import java.util.List;

import se.mharrys.tdw.author.Author;

/**
 * Describes an article for TheDailyWTF.
 */
public class ArticleWTF implements Article {
    private int id;
    private List<Author> authors;
    private String title;
    private Date published;
    private String body;

    public ArticleWTF(int id, Date published, List<Author> authors, String title, String body) {
        this.id = id;
        this.authors = authors;
        this.title = title;
        this.published = published;
        this.body = body;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Date getPublishedDate() {
        return published;
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBody() {
        return body;
    }
}