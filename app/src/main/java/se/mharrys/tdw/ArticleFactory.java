package se.mharrys.tdw;

import java.util.List;

import se.mharrys.tdw.article.Article;
import se.mharrys.tdw.article.ArticleItem;

/**
 * A factory for producing articles.
 */
public interface ArticleFactory {

    /**
     * Create list of recent article items.
     *
     * @param count number of recent articles to retrieve [1,100]
     * @throws InitializationException If fatal error occurs during creation
     * @return list of recent article items
     */
    List<ArticleItem> createRecent(int count) throws InitializationException;

    /**
     * Create article from id.
     *
     * @param id the id of article to create
     * @throws InitializationException If fatal error occurs during creation
     * @return article instance
     */
    Article createFromId(int id) throws InitializationException;

    /**
     * Create list of articles previous to specified article id.
     *
     * @param id the id to start from
     * @param count the number of articles to return
     * @throws InitializationException If fatal error occurs during creation
     * @return  list of articles previous to specified article id
     */
    List<ArticleItem> createAfterId(int id, int count) throws InitializationException;
}
