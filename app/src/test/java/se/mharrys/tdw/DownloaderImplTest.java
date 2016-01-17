package se.mharrys.tdw;

import org.junit.Test;

import java.io.BufferedReader;

import se.mharrys.tdw.remote.Downloader;
import se.mharrys.tdw.remote.DownloaderImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DownloaderImplTest {

    @Test
    public void downloadsExpectedDataInOrder() throws Exception {
        BufferedReader mockReader = mock(BufferedReader.class);
        StringBuilder builder = new StringBuilder();
        Downloader downloader = new DownloaderImpl(mockReader, builder);
        String a = "one", b = "two", c = "three";
        when(mockReader.readLine())
                .thenReturn(a)
                .thenReturn(b)
                .thenReturn(c)
                .thenReturn(null);
        String result = downloader.download();
        assertEquals(result, a + b + c);
        verify(mockReader, times(4)).readLine();
        verify(mockReader, times(1)).close();
    }
}
