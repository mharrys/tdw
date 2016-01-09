package se.mharrys.tdw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.article.ArticleItemWTF;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);

        List<ArticleItem> items = new ArrayList<>();
        items.add(new ArticleItemWTF(1, "Title 1"));
        items.add(new ArticleItemWTF(2, "Title 2"));
        items.add(new ArticleItemWTF(3, "Title 3"));

        ArticleItemAdapter itemsAdapter = new ArticleItemAdapter(this, items);

        ListView articlesView = (ListView) findViewById(R.id.articlesView);
        articlesView.setAdapter(itemsAdapter);
    }
}
