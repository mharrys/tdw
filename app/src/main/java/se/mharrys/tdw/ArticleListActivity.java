package se.mharrys.tdw;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import se.mharrys.tdw.article.ArticleItem;

public class ArticleListActivity extends ListActivity {
    private final int RECENT_COUNT = 15;
    private List<ArticleItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_article_list_activity);

        ArticleFactory factory;
        try {
            factory = new ArticleFactoryWTF();
            items = factory.createRecent(RECENT_COUNT);
        } catch (InitializationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (items != null) {
            ArticleItemAdapter itemsAdapter = new ArticleItemAdapter(this, items);
            setListAdapter(itemsAdapter);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent articleIntent = new Intent(
                ArticleListActivity.this,
                ArticleActivity.class
        );
        articleIntent.putExtra("articleId", items.get(position).getId());
        startActivity(articleIntent);
    }
}
