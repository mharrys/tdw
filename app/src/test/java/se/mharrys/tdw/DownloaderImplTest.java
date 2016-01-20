package se.mharrys.tdw;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import se.mharrys.tdw.remote.Downloader;
import se.mharrys.tdw.remote.DownloaderImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DownloaderImplTest {

    @Test
    public void downloadsExpectedDataInOrder() throws Exception {
        InputStream mockInput = mock(InputStream.class);
        ByteArrayOutputStream mockOuput = mock(ByteArrayOutputStream.class);
        byte[] chunk = "onetwothree".getBytes();
        Downloader downloader = new DownloaderImpl(mockInput, mockOuput, chunk);
        when(mockInput.read(chunk))
                .thenReturn(chunk.length)
                .thenReturn(0);
        when(mockOuput.toByteArray())
                .thenReturn(chunk);
        byte[] result = downloader.download();
        assertEquals(result, chunk);
        verify(mockInput, times(2)).read(chunk);
        verify(mockOuput, times(1)).write(chunk, 0, chunk.length);
        verify(mockOuput, times(1)).toByteArray();
    }
}
