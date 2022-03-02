package org.documentmanager.control.document;

import org.documentmanager.entity.db.Document;
import org.documentmanager.entity.db.DocumentReferenceId;
import org.documentmanager.entity.db.Epic;
import org.documentmanager.entity.db.Label;
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
        return Document.findAll().page(pageIndex, pageSize).stream();
    }

    public void add(final Document document) {
        // When documents are created the source doc isn't necessarily set
        for (final var reference : document.getReferences()) {
            if (reference.getSourceDocument() == null) {
                reference.setSourceDocument(document);
            }
            if (reference.getId() == null) {
                final var documentReferenceId = DocumentReferenceId.create(document.getId(), reference.getSourceDocument().getId());
                reference.setId(documentReferenceId);
            }
        }

        // Assure that the epic exists
        if (document.getEpic() != null) {
            final Epic epic = Epic.findById(document.getEpic().getId());
            document.setEpic(epic);
        }

        final var labelIds = document.getLabels().stream().map(Label::getId).collect(Collectors.toSet());

        final var labels = Label.findByIds(labelIds);

        document.setLabels(labels);

        document.persist();
    }

    public void deleteById(final Long id) {
        final var result = Document.deleteById(id);
        if (!result) {
            throw new DocumentNotDeletableException();
        }
    }
}
