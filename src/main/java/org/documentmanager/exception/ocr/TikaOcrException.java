package org.documentmanager.exception.ocr;

public class TikaOcrException extends OcrException {
    public TikaOcrException() {
        super();
    }

    public TikaOcrException(String message) {
        super(message);
    }

    public TikaOcrException(String message, Throwable cause) {
        super(message, cause);
    }

    public TikaOcrException(TikaOcrException cause) {
        super(cause);
    }

    protected TikaOcrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
