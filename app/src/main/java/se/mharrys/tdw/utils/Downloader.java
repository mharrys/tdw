package se.mharrys.tdw.utils;

import java.io.IOException;
import java.net.URL;

/**
 * The responsibility of this class is to download data from remote location.
 */
public interface Downloader {

    /**
     * Return data from remote location.
     *
     * @param url remote location
     * @throws IOException if something occurs while retrieving content from remote location.
     * @return data from remote location
     */
    String download(URL url) throws IOException;
}
