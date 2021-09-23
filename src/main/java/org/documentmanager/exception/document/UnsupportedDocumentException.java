package org.documentmanager.exception.document;

public class UnsupportedDocumentException extends DocumentException {
  public UnsupportedDocumentException() {
    super();
  }

  public UnsupportedDocumentException(final String message) {
    super(message);
  }

  public UnsupportedDocumentException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UnsupportedDocumentException(final Throwable cause) {
    super(cause);
  }

  protected UnsupportedDocumentException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
