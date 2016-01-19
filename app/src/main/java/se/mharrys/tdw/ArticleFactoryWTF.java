package se.mharrys.tdw;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import se.mharrys.tdw.article.Article;
import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.article.ArticleItemWTF;
import se.mharrys.tdw.article.ArticleWTF;
import se.mharrys.tdw.author.Author;
import se.mharrys.tdw.author.AuthorWTF;
import se.mharrys.tdw.remote.DownloadFilesTask;
import se.mharrys.tdw.remote.DownloaderFactoryImpl;

public class ArticleFactoryWTF implements ArticleFactory {
    private final String baseURL = "http://thedailywtf.com/api/";

    @Override
    public List<ArticleItem> createRecent(int count) throws InitializationException {
        List<ArticleItem> items = new ArrayList<>();
        try {
            List<String> strings = downloadData(baseURL + "articles/recent/" + count);
            JSONArray fetched = new JSONArray(strings.get(0));
            for (int i = 0; i < fetched.length(); i++) {
                JSONObject articleObj = fetched.getJSONObject(i);
                items.add(createArticleItem(articleObj));
            }
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse data from remote location as JSON array", e);
        }
        return items;
    }

    @Override
    public Article createFromId(int id) throws InitializationException {
        try {
            List<String> strings = downloadData(baseURL + "articles/id/" + id);
            JSONObject obj = new JSONObject(strings.get(0));
            return createArticle(obj);
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse data from remote location as JSON object", e);
        }
    }

    private Date createDate(String date) throws InitializationException {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return format.parse(date);
        } catch (ParseException e) {
            throw new InitializationException(
                    "Unable to parse article date", e);
        }
    }

    private Author createAuthor(JSONObject articleObj) throws InitializationException {
        try {
            JSONObject authorObj = articleObj.getJSONObject("Author");
            return new AuthorWTF(
                    authorObj.getString("Name")
            );
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse author JSON data", e);
        }
    }

    private ArticleItem createArticleItem(JSONObject articleObj) throws InitializationException {
        try {
            return new ArticleItemWTF(
                    articleObj.getInt("Id"),
                    articleObj.getString("Title")
            );
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse article item JSON data", e);
        }
    }

    private Article createArticle(JSONObject articleObj) throws InitializationException {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(articleObj));
        try {
            return new ArticleWTF(
                    articleObj.getInt("Id"),
                    createDate(articleObj.getString("PublishedDate")),
                    authors,
                    articleObj.getString("Title"),
                    articleObj.getString("BodyHtml")
            );
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse article JSON data", e);
        }
    }

    private List<String> downloadData(String url) throws InitializationException {
        DownloadFilesTask task = new DownloadFilesTask(new DownloaderFactoryImpl());
        try {
            return task.downloadAll(new URL(url));
        } catch (MalformedURLException e) {
            throw new InitializationException(
                    "Unable to construct API URL for recent articles", e);
        } catch (ExecutionException e) {
            throw new InitializationException(
                    "Error occurred while downloading data" , e);
        } catch (InterruptedException e) {
            throw new InitializationException(
                    "Downloading of data was interrupted", e);
        } catch (IOException e) {
            throw new InitializationException(
                    "Unable to retrieve data from remote location", e);
        }
    }
}