package org.documentmanager.entity.db;

import org.junit.jupiter.api.Test;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;

class DocumentReferenceIdTest {

  @Test
  void checkValidDocumentRefId() {
    final var documentReferenceId = EntityFixture.createDocumentReferenceId();
    assertThat(documentReferenceId, validateTrue());
  }

  @Test
  void checkInvalidDocumentRefMissingSource() {
    final var documentReferenceId = EntityFixture.createDocumentReferenceId();
    documentReferenceId.setSourceId(null);
    assertThat(documentReferenceId, validateFalse());
  }

  @Test
  void checkInvalidDocumentRefMissingTarget() {
    final var documentReferenceId = EntityFixture.createDocumentReferenceId();
    documentReferenceId.setTargetId(null);
    assertThat(documentReferenceId, validateFalse());
  }
}
