package org.documentmanager.entity.db;

import java.time.LocalDateTime;
import java.util.Collections;

public class EntityFixture {

  public static final long EPIC_ID = 10L;
  public static final long LABEL_ID = 10L;
  public static final long ASSET_ID = 10L;
  public static final long FILE_SIZE = 8L;
  public static final long DOCUMENT_ID = 10L;
  public static final long SOURCE_ID = 10L;
  public static final long TARGET_ID = 11L;

  private EntityFixture() {}

  public static Label createLabel() {
    final var label = new Label();
    label.setId(LABEL_ID);
    label.setName("SOME NAME");
    label.setAssociatedDocuments(null);
    return label;
  }

  public static Epic createEpic() {
    final var epic = new Epic();
    epic.setId(EPIC_ID);
    epic.setName("SOME NAME");
    epic.setAssociatedDocuments(null);
    return epic;
  }

  public static Asset createAsset() {
    final var asset = new Asset();
    asset.setCreated(LocalDateTime.now());
    asset.setFileName("test.jpg");
    asset.setHash("abc");
    asset.setId(ASSET_ID);
    asset.setContentType("image/jpg");
    asset.setFileSize(FILE_SIZE);
    asset.setLanguage("de");
    return asset;
  }

  public static Document createBasicDocument() {
    final var document = new Document();
    document.setId(DOCUMENT_ID);
    document.setCreated(LocalDateTime.now());
    document.setLastUpdated(LocalDateTime.now());
    document.setDescription("Some description");
    document.setTitle("Some Title");
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
    documentReferenceId.setSourceId(SOURCE_ID);
    documentReferenceId.setTargetId(TARGET_ID);
    return documentReferenceId;
  }

  public static DocumentReference createDocumentReference() {
    final var documentReference = new DocumentReference();
    documentReference.setReferenceType(DocumentReferenceType.RELATED);
    final var sourceDoc = createBasicDocument();
    sourceDoc.setId(SOURCE_ID);
    final var targetDoc = createBasicDocument();
    targetDoc.setId(TARGET_ID);
    final var documentReferenceId = createDocumentReferenceId();
    documentReference.setId(documentReferenceId);
    documentReference.setSourceDocument(sourceDoc);
    documentReference.setTargetDocument(targetDoc);
    return documentReference;
  }
}
