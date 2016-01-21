package se.mharrys.tdw;

import org.junit.Test;

import se.mharrys.tdw.article.Article;
import se.mharrys.tdw.utils.CreateFromIdStrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateFromIdStrategyTest {

    @Test
    public void createsCorrectArticle() throws Exception {
        int id = 42;
        Article expected = mock(Article.class);
        when(expected.getId()).thenReturn(id);
        ArticleFactory mockFactory = mock(ArticleFactory.class);
        when(mockFactory.createFromId(id)).thenReturn(expected);

        CreateFromIdStrategy strategy = new CreateFromIdStrategy(id, mockFactory);
        Article actual = strategy.create();

        verify(mockFactory, times(1)).createFromId(id);
        assertEquals(expected, actual);
    }
}
