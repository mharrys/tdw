package se.mharrys.tdw.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownloaderImpl implements Downloader {
    private InputStream input;
    private ByteArrayOutputStream output;
    private byte[] chunk;

    public DownloaderImpl(InputStream input, ByteArrayOutputStream output, byte[] chunk) {
        this.input = input;
        this.output = output;
        this.chunk = chunk;
    }

    @Override
    public byte[] download() throws IOException {
        int bytesRead;
        while ((bytesRead = input.read(chunk)) > 0) {
            output.write(chunk, 0, bytesRead);
        }
        return output.toByteArray();
    }
}