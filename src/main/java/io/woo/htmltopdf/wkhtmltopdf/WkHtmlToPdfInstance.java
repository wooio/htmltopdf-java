package io.woo.htmltopdf.wkhtmltopdf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class WkHtmlToPdfInstance {
    static final WkHtmlToPdfInstance instance = new WkHtmlToPdfInstance();

    private final ExecutorService executorService;
    private WkHtmlToPdf wkHtmlToPdf;

    private WkHtmlToPdfInstance() {
        executorService = Executors.newFixedThreadPool(1, r -> {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(true);
            return thread;
        });
    }

    public <T> T withInstance(Function<WkHtmlToPdf,T> fn) {
        try {
            if (wkHtmlToPdf == null) {
                wkHtmlToPdf = executorService.submit(WkHtmlToPdfLoader::load).get();
            }
            return executorService.submit(() -> fn.apply(wkHtmlToPdf)).get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw new IllegalStateException(e);
        } catch (InterruptedException e) {
            throw new IllegalStateException("interrupted", e);
        }
    }
}
