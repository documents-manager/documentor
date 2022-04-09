package org.documentmanager.control.document;

import org.documentmanager.entity.db.*;
import org.documentmanager.entity.dto.asset.AssetDto;
import org.documentmanager.entity.dto.document.DocumentDto;
import org.documentmanager.exception.document.DocumentNotDeletableException;
import org.documentmanager.mapper.DocumentMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class DocumentService {
    @Inject
    DocumentMapper mapper;

    public Optional<Document> findByIdOptional(final Long id) {
        return Document.findByIdOptional(id);
    }

    public Stream<Document> list(final int pageIndex, final int pageSize) {
        return Document.findAll().page(pageIndex, pageSize).stream();
    }

    public void add(final Document document) {
        // When documents are created the source doc isn't necessarily set
        if (document.getReferences() != null) {
            for (final var reference : document.getReferences()) {
                if (reference.getSourceDocument() == null) {
                    reference.setSourceDocument(document);
                }
                if (reference.getId() == null) {
                    final var documentReferenceId = DocumentReferenceId.create(document.getId(), reference.getTargetDocument().getId());
                    reference.setId(documentReferenceId);
                }
            }

        }
        // Assure that the epic exists
        if (document.getEpic() != null) {
            final Epic epic = Epic.findById(document.getEpic().getId());
            document.setEpic(epic);
        }

        if (document.getLabels() != null) {
            final var labelIds = document.getLabels().stream().map(Label::getId).collect(Collectors.toSet());

            final var labels = Label.findByIds(labelIds);

            document.setLabels(labels);
        }

        if (document.getAssets() != null) {
            final var assetIds = document.getAssets().stream().map(Asset::getId).collect(Collectors.toSet());
            final var assets = Asset.findByIds(assetIds);
            for (Asset asset : assets) {
                asset.setDocument(document);
            }
            document.setAssets(assets);
        }


        document.persist();
    }

    public void deleteById(final Long id) {
        final var result = Document.deleteById(id);
        if (!result) {
            throw new DocumentNotDeletableException();
        }
    }

    public void update(final Document document, final DocumentDto update) {
        // Update simple properties
        document.setTitle(update.getTitle());
        document.setDescription(update.getDescription());

        // Update more complex properties
        final var targetReferences = document.getReferences().stream().map(documentReference -> mapper.toLinkDto(documentReference.getTargetDocument())).collect(Collectors.toList());
        final var referencesToAdd = new ArrayList<DocumentReference>();
        final var referencesToDelete = new ArrayList<DocumentReference>();

        for (final var reference : update.getReferences()) {
            if (!targetReferences.contains(reference.getTargetDocument())) {
                final var documentReferenceId = DocumentReferenceId.create(document.getId(), reference.getTargetDocument().getId());
                final var documentReference = new DocumentReference();
                documentReference.setSourceDocument(document);
                documentReference.setTargetDocument(Document.findById(reference.getTargetDocument().getId()));
                documentReference.setReferenceType(reference.getReferenceType());
                documentReference.setId(documentReferenceId);
                referencesToAdd.add(documentReference);
            }
        }

        for (final var reference : document.getReferences()) {
            final var documentReferenceDto = mapper.toDto(reference);
            if (!update.getReferences().contains(documentReferenceDto)) {
                referencesToDelete.add(reference);
            }
        }

        document.getReferences().removeAll(referencesToDelete);
        document.getReferences().addAll(referencesToAdd);

        if (update.getEpic() != null) {
            final Epic epic = Epic.findById(update.getEpic().getId());
            document.setEpic(epic);
        } else {
            document.setEpic(null);
        }

        final var labelIds = update.getLabels().stream().map(Label::getId).collect(Collectors.toSet());

        final var labels = Label.findByIds(labelIds);

        document.setLabels(labels);

        final var assetIds = update.getAssets().stream().map(AssetDto::getId).collect(Collectors.toSet());
        final var assetsToAdd = Asset.findByIds(assetIds);
        assetsToAdd.removeAll(document.getAssets());
        final var assetsToDelete = new ArrayList<Asset>();

        for (final var asset : document.getAssets()) {
            final var assetDto = mapper.toAssetDto(asset);
            if (!update.getAssets().contains(assetDto)) {
                assetsToDelete.add(asset);
            }
        }

        document.getAssets().removeAll(assetsToDelete);
        document.getAssets().addAll(assetsToAdd);

        for (final Asset asset : assetsToAdd) {
            asset.setDocument(document);
        }
    }
}
