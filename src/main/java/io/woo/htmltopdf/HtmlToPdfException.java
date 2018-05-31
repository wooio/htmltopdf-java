package io.woo.htmltopdf;

public class HtmlToPdfException extends RuntimeException {
    public HtmlToPdfException() {
    }

    public HtmlToPdfException(String message) {
        super(message);
    }

    public HtmlToPdfException(String message, Throwable cause) {
        super(message, cause);
    }

    public HtmlToPdfException(Throwable cause) {
        super(cause);
    }

    public HtmlToPdfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
