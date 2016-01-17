package se.mharrys.tdw.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DownloaderFactoryImpl implements DownloaderFactory {
    private final int connectTimeout;
    private final int readTimeout;

    /**
     * Construct downloader factory with default timeout values.
     */
    public DownloaderFactoryImpl() {
        connectTimeout = 10000;
        readTimeout = 2000;
    }

    /**
     * Construct downloader factory with specified timeout values.
     */
    public DownloaderFactoryImpl(int connectTimeout, int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public Downloader createDownloader(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        InputStreamReader input = new InputStreamReader(conn.getInputStream());
        BufferedReader reader = new BufferedReader(input);
        StringBuilder builder = new StringBuilder();
        return new DownloaderImpl(reader, builder);
    }
}
