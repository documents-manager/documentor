package org.documentmanager.control;

import org.documentmanager.entity.db.Document;
import org.documentmanager.exception.document.DocumentNotDeletableException;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class DocumentService {

    public Optional<Document> findByIdOptional(Long id) {
        return Document.findByIdOptional(id);
    }

    public Stream<Document> list(int pageIndex, int pageSize) {
        return Document.findAll().page(pageIndex, pageSize).stream();
    }

    public void add(Document document) {
        document.persist();
    }

    public void deleteById(Long id) {
        var result = Document.deleteById(id);
        if (!result) {
            throw new DocumentNotDeletableException();
        }
    }
}
