package se.mharrys.tdw.utils;

import java.util.List;

import se.mharrys.tdw.InitializationException;
import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.ArticleFactory;

/**
 * The responsibility of this class is to encapsulate the creation of article items after a
 * specified article id.
 */
public class CreateAfterIdStrategy implements CreateArticleStrategy<List<ArticleItem>> {
    private int id;
    private int count;
    private ArticleFactory factory;

    public CreateAfterIdStrategy(int id, int count, ArticleFactory factory) {
        this.id = id;
        this.count = count;
        this.factory = factory;
    }

    @Override
    public List<ArticleItem> create() throws InitializationException {
        return factory.createAfterId(id, count);
    }
}
