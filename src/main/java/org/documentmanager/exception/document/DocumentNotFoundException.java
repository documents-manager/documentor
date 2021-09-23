package org.documentmanager.exception.document;

public class DocumentNotFoundException extends DocumentException {
  public DocumentNotFoundException(final Long documentId) {
    this(documentId.toString());
  }

  public DocumentNotFoundException(final String documentId) {
    super(String.format("Document with id %s not found", documentId));
  }
}
