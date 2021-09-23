package org.documentmanager.exception.document;

public class DocumentException extends RuntimeException {
  public DocumentException() {
    super();
  }

  public DocumentException(final String message) {
    super(message);
  }

  public DocumentException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DocumentException(final Throwable cause) {
    super(cause);
  }

  protected DocumentException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
