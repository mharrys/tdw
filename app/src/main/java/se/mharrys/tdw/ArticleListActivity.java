package se.mharrys.tdw;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.utils.CreateAfterIdStrategy;
import se.mharrys.tdw.utils.CreateArticleTask;
import se.mharrys.tdw.utils.CreateRecentStrategy;
import se.mharrys.tdw.utils.OnTaskComplete;

public class ArticleListActivity extends ListActivity implements AbsListView.OnScrollListener, OnTaskComplete<List<ArticleItem>> {
    private final int RECENT_COUNT = 20;
    private final int PAGE_COUNT   = 10;

    private ArticleFactory factory;
    private CreateArticleTask<List<ArticleItem>> task;
    private ArticleItemAdapter itemsAdapter;
    private View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_article_list_activity);

        try {
            factory = new ArticleFactoryWTF();
        } catch (InitializationException e) {
            showError(e.getMessage());
        }

        if (factory != null) {
            itemsAdapter = new ArticleItemAdapter(this);
            setListAdapter(itemsAdapter);
            runCreateRecentTask();

            // constant spin around center
            RotateAnimation rotate = new RotateAnimation(
                    0.0f, 360.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotate.setDuration(800);
            rotate.setRepeatMode(Animation.RESTART);
            rotate.setRepeatCount(Animation.INFINITE);
            rotate.setInterpolator(new LinearInterpolator());

            // footer to display when fetching more data
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            loadingView = inflater.inflate(R.layout.layout_article_list_item_loading, null);
            loadingView.startAnimation(rotate);

            getListView().setOnScrollListener(this);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent articleIntent = new Intent(
                ArticleListActivity.this,
                ArticleActivity.class
        );
        articleIntent.putExtra("articleId", itemsAdapter.getItem(position).getId());
        startActivity(articleIntent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        final boolean lastVisible = totalItemCount - (firstVisibleItem + visibleItemCount) == 0;
        if (task == null && lastVisible) {
            getListView().addFooterView(loadingView);
            final int id = itemsAdapter.getItem(itemsAdapter.getCount() - 1).getId();
            runCreateAfterIdTask(id);
        }
    }

    public void onTaskCompleted(List<ArticleItem> items, Exception e) {
        if (e != null) {
            showError(e.getMessage());
        } else {
            itemsAdapter.addItems(items);
        }
        task = null;
        getListView().removeFooterView(loadingView);
    }

    private void runCreateRecentTask() {
        CreateRecentStrategy strategy = new CreateRecentStrategy(RECENT_COUNT, factory);
        task = new CreateArticleTask<>(strategy, this);
        task.execute();
    }

    private void runCreateAfterIdTask(int id) {
        CreateAfterIdStrategy strategy = new CreateAfterIdStrategy(id, PAGE_COUNT, factory);
        task = new CreateArticleTask<>(strategy, this);
        task.execute();
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
