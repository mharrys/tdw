package se.mharrys.tdw.utils;

import se.mharrys.tdw.InitializationException;
import se.mharrys.tdw.article.Article;
import se.mharrys.tdw.ArticleFactory;

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
