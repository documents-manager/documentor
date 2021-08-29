package org.documentmanager.entity.db;

import java.lang.ref.Reference;
import java.time.LocalDateTime;
import java.util.Collections;

public class EntityFixture {
    public static Label createLabel() {
        final var label = new Label();
        label.setId(10L);
        label.setName("SOME NAME");
        label.setVersion(1);
        label.setAssociatedDocuments(null);
        return label;
    }

    public static Epic createEpic() {
        final var epic = new Epic();
        epic.setId(10L);
        epic.setName("SOME NAME");
        epic.setVersion(1);
        epic.setAssociatedDocuments(null);
        return epic;
    }

    public static Asset createAsset() {
        final var asset = new Asset();
        asset.setCreated(LocalDateTime.now());
        asset.setFileName("test.jpg");
        asset.setHash("abc");
        asset.setId(10L);
        asset.setMimeType("image/jpg");
        asset.setFileSize(8L);
        return asset;
    }

    public static Document createBasicDocument() {
        final var document = new Document();
        document.setId(10L);
        document.setCreated(LocalDateTime.now());
        document.setLastUpdated(LocalDateTime.now());
        document.setDescription("Some description");
        document.setTitle("Some Title");
        document.setVersion(1);
        return document;
    }

    public static Document createDocumentWithAsset() {
        final var document = createBasicDocument();
        final var asset = createAsset();
        document.setAssets(Collections.singletonList(asset));
        asset.setDocument(document);
        return document;
    }

    public static Asset createAssetWithDocument() {
        return createDocumentWithAsset().getAssets().get(0);
    }

    public static DocumentReferenceId createDocumentReferenceId() {
        final var documentReferenceId = new DocumentReferenceId();
        documentReferenceId.setSourceId(10L);
        documentReferenceId.setTargetId(11L);
        return documentReferenceId;
    }

    public static DocumentReference createDocumentReference() {
        final var documentReference = new DocumentReference();
        documentReference.setReferenceType(DocumentReferenceType.RELATED);
        final var sourceDoc = createBasicDocument();
        sourceDoc.setId(10L);
        final var targetDoc = createBasicDocument();
        targetDoc.setId(11L);
        final var documentReferenceId = createDocumentReferenceId();
        documentReference.setId(documentReferenceId);
        documentReference.setSourceDocument(sourceDoc);
        documentReference.setTargetDocument(targetDoc);
        return documentReference;
    }
}
