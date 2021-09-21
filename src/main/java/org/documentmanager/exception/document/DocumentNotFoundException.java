package org.documentmanager.exception.document;

public class DocumentNotFoundException extends DocumentException {
    public DocumentNotFoundException(Long documentId) {
        this(documentId.toString());
    }

    public DocumentNotFoundException(String documentId) {
        super(String.format("Document with id %s not found", documentId));
    }
}
