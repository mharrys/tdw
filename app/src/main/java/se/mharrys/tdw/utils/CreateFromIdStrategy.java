package se.mharrys.tdw.utils;

import se.mharrys.tdw.InitializationException;
import se.mharrys.tdw.article.Article;
import se.mharrys.tdw.ArticleFactory;

/**
 * The responsibility of this class is to encapsulate the creation of article from id.
 */
public class CreateFromIdStrategy implements CreateArticleStrategy<Article> {
    private int id;
    private ArticleFactory factory;

    public CreateFromIdStrategy(int id, ArticleFactory factory) {
        this.id = id;
        this.factory = factory;
    }

    @Override
    public Article create() throws InitializationException {
        return factory.createFromId(id);
    }
}
