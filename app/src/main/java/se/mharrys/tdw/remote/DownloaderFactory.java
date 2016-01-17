package se.mharrys.tdw.remote;

import java.io.IOException;
import java.net.URL;

/**
 * A factory for producing a downloader.
 */
public interface DownloaderFactory {

    /**
     * Create downloader for remote location.
     *
     * @param url the remote location
     * @return downloader
     * @throws IOException If unable to create downloader
     */
    Downloader createDownloader(URL url) throws IOException;
}
