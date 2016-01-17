package se.mharrys.tdw;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import se.mharrys.tdw.article.ArticleItem;
import se.mharrys.tdw.article.ArticleItemWTF;
import se.mharrys.tdw.remote.DownloadFilesTask;
import se.mharrys.tdw.remote.DownloaderFactoryImpl;

public class ArticleFactoryWTF implements ArticleFactory {
    private final String baseURL = "http://thedailywtf.com/api/";

    @Override
    public List<ArticleItem> createRecent(int count) throws InitializationException {
        List<ArticleItem> items = new ArrayList<>();
        try {
            DownloadFilesTask task = new DownloadFilesTask(new DownloaderFactoryImpl());
            URL recent = new URL(baseURL + "articles/recent/" + count);
            List<String> strings = task.downloadAll(recent);
            JSONArray fetched = new JSONArray(strings.get(0));
            for (int i = 0; i < fetched.length(); i++) {
                JSONObject obj = fetched.getJSONObject(i);
                items.add(new ArticleItemWTF(obj.getInt("Id"), obj.getString("Title")));
            }
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
        } catch (JSONException e) {
            throw new InitializationException(
                    "Unable to parse data from remote location as JSON array", e);
        }
        return items;
    }
}