package org.documentmanager.exception.document;

public class UnsupportedDocumentException extends DocumentException {
    public UnsupportedDocumentException() {
        super();
    }

    public UnsupportedDocumentException(String message) {
        super(message);
    }

    public UnsupportedDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedDocumentException(Throwable cause) {
        super(cause);
    }

    protected UnsupportedDocumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
