package se.mharrys.tdw.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * The responsibility of this class is to download data from one or more remote locations in
 * a separate thread.
 */
public class DownloadFilesTask extends AsyncTask<URL, Void, List<String>> {
    private Downloader downloader;
    private IOException error;

    /**
     * Construct task.
     *
     * @param downloader the downloader to use for each remote location
     */
    public DownloadFilesTask(Downloader downloader) {
        this.downloader = downloader;
    }

    /**
     * Return data from remote location.
     *
     * @param urls remote locations
     * @return null if error occurred, or list of data for each url
     * @throws ExecutionException If the computation threw an exception
     * @throws InterruptedException If the current thread was interrupted while waiting
     * @throws IOException If data was not retrieved from remote location
     */
    public List<String> downloadAll(URL... urls) throws ExecutionException, InterruptedException, IOException {
        execute(urls);
        List<String> result = get();
        if (result == null)
            throw error;
        return result;
    }

    @Override
    protected List<String> doInBackground(URL... urls) {
        List<String> contents = new ArrayList<>();
        for (URL url : urls) {
            try {
                contents.add(downloader.download(url));
            } catch (IOException e) {
                error = e;
                return null;
            }
        }
        return contents;
    }
}
