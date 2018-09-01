package io.woo.htmltopdf;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class HtmlToPdfTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void convertReturnsTrueWhenConversionSuccessful() throws IOException {
        File file = tempFolder.newFile();
        boolean result = HtmlToPdf.create()
                .object(HtmlToPdfObject.forHtml("<p>Test</p>"))
                .convert(file.getPath());

        assertTrue(result);
    }

    @Test
    public void convertReturnsFalseWhenConversionFailed() throws IOException {
        File file = tempFolder.newFile();
        boolean result = HtmlToPdf.create()
                .object(HtmlToPdfObject.forUrl("file:///path/that/does/not/exist"))
                .convert(file.getPath());
        assertFalse(result);
    }

    @Test
    public void convertReturnsFalseWhenNoObjectsSpecified() throws IOException {
        File file = tempFolder.newFile();
        boolean result = HtmlToPdf.create().convert(file.getPath());
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingObjectForHtmlWithNoContentThrowsException() {
        HtmlToPdfObject.forHtml("");
    }

    @Test(expected = HtmlToPdfException.class)
    public void convertToInputStreamThrowsExceptionOnFailure() {
        HtmlToPdf.create()
                .object(HtmlToPdfObject.forUrl("file:///path/that/does/not/exist"))
                .convert();
    }

    @Test
    public void itDoesNotHangWhenAccessedByMultipleThreads() throws InterruptedException {
        int concurrency = 5;

        AtomicReference<InterruptedException> interrupted = new AtomicReference<>();
        ExecutorService service = Executors.newFixedThreadPool(concurrency);
        CountDownLatch latch = new CountDownLatch(concurrency);

        AtomicInteger converted = new AtomicInteger();

        IntStream.range(0, concurrency)
                .<Runnable>mapToObj((i) -> () -> {
                    latch.countDown();
                    try {
                        latch.await();
                    }
                    catch (InterruptedException e) {
                        interrupted.set(e);
                    }
                    try {
                        HtmlToPdf.create()
                                .object(HtmlToPdfObject.forHtml("<p>test</p>"))
                                .convert();
                        converted.getAndIncrement();
                    } catch (HtmlToPdfException e) {
                        e.printStackTrace();
                    }
                })
                .forEach(service::submit);
        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);

        if (interrupted.get() != null) {
            throw interrupted.get();
        }

        assertEquals(concurrency, converted.get());
    }

    @Test
    public void itReleasesHandleToPdfFileWhenConversionIsDone() throws IOException {
        File file = tempFolder.newFile();
        HtmlToPdf.create()
                .object(HtmlToPdfObject.forHtml("<p>test</p>"))
                .convert(file.getPath());
        assertTrue(file.renameTo(file));
    }
}