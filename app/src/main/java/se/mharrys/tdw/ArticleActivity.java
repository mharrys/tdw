package se.mharrys.tdw;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import se.mharrys.tdw.article.Article;
import se.mharrys.tdw.article.factory.ArticleFactory;
import se.mharrys.tdw.article.factory.ArticleFactoryWTF;
import se.mharrys.tdw.utils.CreateArticleTask;
import se.mharrys.tdw.article.factory.CreateFromIdStrategy;
import se.mharrys.tdw.utils.OnTaskComplete;

public class ArticleActivity extends Activity implements OnTaskComplete<Article> {
    private ArticleFactory factory;
    private CreateArticleTask<Article> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_article_activity);

        try {
            factory = new ArticleFactoryWTF();
        } catch (InitializationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (factory != null) {
            Bundle extras = getIntent().getExtras();
            runCreateFromIdTask(extras.getInt("articleId"));
        }
    }

    @Override
    public void onTaskCompleted(Article article, Exception e) {
        if (e != null) {
            showError(e.getMessage());
        } else {
            TextView articleTitle = (TextView) findViewById(R.id.articleTitle);
            articleTitle.setText(article.getTitle());
            TextView articleMeta = (TextView) findViewById(R.id.articleMeta);
            articleMeta.setText(buildMeta(article));
            TextView articleBody = (TextView) findViewById(R.id.articleBody);
            articleBody.setText(Html.fromHtml(article.getBody()));
        }
        task = null;
    }

    private String buildMeta(Article article) {
        final String name = article.getAuthor().getName();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String date = dateFormat.format(article.getPublishedDate());
        return getResources().getString(R.string.article_meta, name, date);
    }

    private void runCreateFromIdTask(int id) {
        CreateFromIdStrategy strategy = new CreateFromIdStrategy(id, factory);
        task = new CreateArticleTask<>(strategy, this);
        task.execute();
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
