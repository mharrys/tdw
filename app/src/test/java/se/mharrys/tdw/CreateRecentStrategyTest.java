package se.mharrys.tdw;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.utils.CreateRecentStrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateRecentStrategyTest {

    @Test
    public void createsCorrectArticle() throws Exception {
        int count = 10;
        List<ArticleItem> expected = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ArticleItem item = mock(ArticleItem.class);
            when(item.getId()).thenReturn(i);
        }
        ArticleFactory mockFactory = mock(ArticleFactory.class);
        when(mockFactory.createRecent(count)).thenReturn(expected);

        CreateRecentStrategy strategy = new CreateRecentStrategy(count, mockFactory);
        List<ArticleItem> actual = strategy.create();

        verify(mockFactory, times(1)).createRecent(count);
        assertEquals(expected, actual);
    }
}
