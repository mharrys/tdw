package se.mharrys.tdw.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloaderFactoryImpl implements DownloaderFactory {
    private final int connectTimeout;
    private final int readTimeout;
    private final int chunkSize;

    /**
     * Construct downloader factory with default timeout values.
     */
    public DownloaderFactoryImpl() {
        connectTimeout = 15000;
        readTimeout = 5000;
        chunkSize = 4096;
    }

    /**
     * Construct downloader factory with specified timeout values.
     */
    public DownloaderFactoryImpl(int connectTimeout, int readTimeout, int chunkSize) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.chunkSize = chunkSize;
    }

    public Downloader createDownloader(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        InputStream input = conn.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        return new DownloaderImpl(input, output, new byte[chunkSize]);
    }
}
