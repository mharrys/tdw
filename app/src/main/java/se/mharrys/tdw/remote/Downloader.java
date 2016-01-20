package se.mharrys.tdw.remote;

import java.io.IOException;

/**
 * The responsibility of this class is to download data from remote location.
 */
public interface Downloader {

    /**
     * Return data from remote location.
     *
     * @throws IOException if something occurs while retrieving content from remote location.
     * @return data from remote location
     */
    byte[] download() throws IOException;
}
