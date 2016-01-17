package se.mharrys.tdw.remote;

import java.io.BufferedReader;
import java.io.IOException;

public class DownloaderImpl implements Downloader {
    private BufferedReader reader;
    private StringBuilder builder;

    public DownloaderImpl(BufferedReader reader, StringBuilder builder) {
        this.reader = reader;
        this.builder = builder;
    }

    @Override
    public String download() throws IOException {
        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line);
        reader.close();
        return builder.toString();
    }
}