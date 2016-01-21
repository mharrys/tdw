package se.mharrys.tdw.utils;

import se.mharrys.tdw.InitializationException;

/**
 * The responsibility of this class is to encapsulate a article creation strategy.
 */
public interface CreateArticleStrategy<T> {

    /**
     * Create article(s).
     *
     * @throws InitializationException If fatal error occurs during creation
     * @return created article(s)
     */
    T create() throws InitializationException;
}
