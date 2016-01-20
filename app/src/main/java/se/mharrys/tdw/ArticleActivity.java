package se.mharrys.tdw;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import se.mharrys.tdw.article.Article;

public class ArticleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_article_activity);

        Article article = null;
        ArticleFactory factory;
        try {
            factory = new ArticleFactoryWTF();
            Bundle extras = getIntent().getExtras();
            article = factory.createFromId(extras.getInt("articleId"));
        } catch (InitializationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (article != null) {
            TextView articleTitle = (TextView) findViewById(R.id.articleTitle);
            articleTitle.setText(article.getTitle());
            TextView articleMeta = (TextView) findViewById(R.id.articleMeta);
            articleMeta.setText(buildMeta(article));
            TextView articleBody = (TextView) findViewById(R.id.articleBody);
            articleBody.setText(Html.fromHtml(article.getBody()));
        }
    }

    private String buildMeta(Article article) {
        final String name = article.getAuthor().getName();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String date = dateFormat.format(article.getPublishedDate());
        return getResources().getString(R.string.article_meta, name, date);
    }
}
