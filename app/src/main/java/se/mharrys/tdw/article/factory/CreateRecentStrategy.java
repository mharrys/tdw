package se.mharrys.tdw.article.factory;

import java.util.List;

import se.mharrys.tdw.InitializationException;
import se.mharrys.tdw.article.ArticleItem;

/**
 * The responsibility of this class is to encapsulate the creation of recent article items.
 */
public class CreateRecentStrategy implements CreateArticleStrategy<List<ArticleItem>> {
    private int count;
    private ArticleFactory factory;

    public CreateRecentStrategy(int count, ArticleFactory factory) {
        this.count = count;
        this.factory = factory;
    }

    @Override
    public List<ArticleItem> create() throws InitializationException {
        return factory.createRecent(count);
    }
}
