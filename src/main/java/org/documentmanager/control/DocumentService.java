package org.documentmanager.control;

import io.quarkus.panache.common.Parameters;
import org.documentmanager.entity.db.Document;
import org.documentmanager.entity.projections.DocumentId;
import org.documentmanager.exception.document.DocumentNotDeletableException;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class DocumentService {

  public Optional<Document> findByIdOptional(final Long id) {
    return Document.findByIdOptional(id);
  }

  public Stream<Document> list(final int pageIndex, final int pageSize) {
    final var ids =
        Document.findAll().page(pageIndex, pageSize).project(DocumentId.class).stream()
            .map(documentId -> documentId.id)
            .collect(Collectors.toList());
    return Document.find(
        "select d from Document d join fetch d.assets a join fetch a.metadata where d.id in :ids",
        Parameters.with("ids", ids))
        .stream();
  }

  public void add(final Document document) {
    document.persist();
  }

  public void deleteById(final Long id) {
    final var result = Document.deleteById(id);
    if (!result) {
      throw new DocumentNotDeletableException();
    }
  }
}
