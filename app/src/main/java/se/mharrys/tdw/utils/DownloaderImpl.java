package se.mharrys.tdw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DownloaderImpl implements Downloader {

    @Override
    public String download(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(2000);
        InputStreamReader input = new InputStreamReader(conn.getInputStream());
        BufferedReader reader = new BufferedReader(input);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line);
        reader.close();
        return builder.toString();
    }
}