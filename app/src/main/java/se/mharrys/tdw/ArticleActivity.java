package se.mharrys.tdw;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import se.mharrys.tdw.article.Article;

public class ArticleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_article_activity);

        Bundle extras = getIntent().getExtras();

        Article article = null;
        ArticleFactory factory;
        try {
            factory = new ArticleFactoryWTF();
            article = factory.createFromId(extras.getInt("articleId"));
        } catch (InitializationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (article != null) {
            TextView articleTitle = (TextView) findViewById(R.id.articleTitle);
            articleTitle.setText(article.getTitle());
            TextView articleMeta = (TextView) findViewById(R.id.articleMeta);
            final String name = article.getAuthors().get(0).getName();
            final String date = article.getPublishedDate().toString();
            final String meta = "by " + name + " on " + date;
            articleMeta.setText(meta);
            TextView articleBody = (TextView) findViewById(R.id.articleBody);
            articleBody.setText(Html.fromHtml(article.getBody()));
        }
    }
}
