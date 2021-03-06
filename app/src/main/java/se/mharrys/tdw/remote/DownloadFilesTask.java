package se.mharrys.tdw.remote;

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
public class DownloadFilesTask extends AsyncTask<URL, Void, List<byte[]>> {
    private DownloaderFactory factory;
    private IOException error;

    /**
     * Construct task.
     *
     * @param factory the factory to create downloader for each remote location
     */
    public DownloadFilesTask(DownloaderFactory factory) {
        this.factory = factory;
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
    public List<byte[]> downloadAll(URL... urls) throws ExecutionException, InterruptedException, IOException {
        execute(urls);
        List<byte[]> result = get();
        if (error != null)
            throw error;
        return result;
    }

    @Override
    protected List<byte[]> doInBackground(URL... urls) {
        List<byte[]> contents = new ArrayList<>();
        error = null;
        for (URL url : urls) {
            try {
                Downloader downloader = factory.createDownloader(url);
                contents.add(downloader.download());
            } catch (IOException e) {
                error = e;
                break;
            }
        }
        return contents;
    }
}
