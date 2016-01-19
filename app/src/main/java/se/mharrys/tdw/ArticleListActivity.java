package se.mharrys.tdw;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import se.mharrys.tdw.article.ArticleItem;

public class ArticleListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);

        ArticleFactory factory;
        List<ArticleItem> items = null;
        try {
            factory = new ArticleFactoryWTF();
            items = factory.createRecent(15);
        } catch (InitializationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (items != null) {
            ArticleItemAdapter itemsAdapter = new ArticleItemAdapter(this, items);
            setListAdapter(itemsAdapter);
        }
    }
}
