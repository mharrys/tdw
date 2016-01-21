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

import se.mharrys.tdw.article.Article;
import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.article.ArticleItemWTF;
import se.mharrys.tdw.article.ArticleWTF;
import se.mharrys.tdw.author.Author;
import se.mharrys.tdw.author.AuthorWTF;
import se.mharrys.tdw.remote.Downloader;
import se.mharrys.tdw.remote.DownloaderFactory;
import se.mharrys.tdw.remote.DownloaderFactoryImpl;

public class ArticleFactoryWTF implements ArticleFactory {
    private final URL base;
    private final URL baseRecent;
    private final URL baseId;
    private DownloaderFactory factory;

    public ArticleFactoryWTF() throws InitializationException {
        try {
            this.base = new URL("http://thedailywtf.com/api/");
            this.baseRecent = new URL(base, "articles/recent/");
            this.baseId = new URL(base, "articles/id/");
        } catch (MalformedURLException e) {
            throw new InitializationException(
                    "Unable to construct API URLs", e);
        }
        this.factory = new DownloaderFactoryImpl();
    }

    @Override
    public List<ArticleItem> createRecent(int count) throws InitializationException {
        List<ArticleItem> items = new ArrayList<>();
        try {
            URL recentUrl = createWithRecent(count);
            JSONArray fetched = new JSONArray(download(recentUrl));
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
            URL idUrl = createWithId(id);
            JSONObject obj = new JSONObject(download(idUrl));
            return createArticle(obj);
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse data from remote location as JSON object", e);
        }
    }

    @Override
    public List<ArticleItem> createAfterId(int id, int count) throws InitializationException {
        List<ArticleItem> items = new ArrayList<>();
        try {
            // There is no support for pagination in the API, this is a workaround
            int next = id;
            for (int i = 0; i < count; i++) {
                URL idUrl = createWithId(next);
                JSONObject articleObj = new JSONObject(download(idUrl));
                items.add(createArticleItem(articleObj));
                next = articleObj.getInt("PreviousArticleId");
            }
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse data from remote location as JSON object", e);
        }
        items.remove(0); // don't include specified id
        return items;
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
        try {
            return new ArticleWTF(
                    articleObj.getInt("Id"),
                    createDate(articleObj.getString("PublishedDate")),
                    createAuthor(articleObj),
                    articleObj.getString("Title"),
                    articleObj.getString("BodyHtml")
            );
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse article JSON data", e);
        }
    }

    private URL createWithRecent(int count) throws InitializationException {
        URL recentUrl;
        try {
            recentUrl = new URL(baseRecent, "" + count);
        } catch (MalformedURLException e) {
            throw new InitializationException(
                    "Unable to create API URL for recent articles", e);
        }
        return recentUrl;
    }

    private URL createWithId(int id) throws InitializationException {
        URL idUrl;
        try {
            idUrl = new URL(baseId, "" + id);
        } catch (MalformedURLException e) {
            throw new InitializationException(
                    "Unable to create API URL for article id", e);
        }
        return idUrl;
    }

    private String download(URL url) throws InitializationException {
        String data;
        try {
            Downloader downloader = factory.createDownloader(url);
            data = new String(downloader.download());
        }catch (IOException e) {
            throw new InitializationException(
                    "Unable to retrieve data from remote location", e);
        }
        return data;
    }
}