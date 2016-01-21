package se.mharrys.tdw;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.utils.CreateAfterIdStrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateAfterIdStrategyTest {

    @Test
    public void createsCorrectArticles() throws Exception {
        int id = 42;
        int count = 10;
        List<ArticleItem> expected = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ArticleItem item = mock(ArticleItem.class);
            when(item.getId()).thenReturn(i);
        }
        ArticleFactory mockFactory = mock(ArticleFactory.class);
        when(mockFactory.createAfterId(id, count)).thenReturn(expected);

        CreateAfterIdStrategy strategy = new CreateAfterIdStrategy(id, count, mockFactory);
        List<ArticleItem> actual = strategy.create();

        verify(mockFactory, times(1)).createAfterId(id, count);
        assertEquals(expected, actual);
    }
}
