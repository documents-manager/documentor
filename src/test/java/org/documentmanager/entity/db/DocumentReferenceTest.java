package org.documentmanager.entity.db;

import org.junit.jupiter.api.Test;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;

class DocumentReferenceTest {

  @Test
  void checkValidDocumentReference() {
    final var documentReference = EntityFixture.createDocumentReference();
    assertThat(documentReference, validateTrue());
  }

  @Test
  void checkInvalidDocumentReferenceMissingSource() {
    final var documentReference = EntityFixture.createDocumentReference();
    documentReference.setSourceDocument(null);
    assertThat(documentReference, validateFalse());
  }

  @Test
  void checkInvalidDocumentReferenceMissingTarget() {
    final var documentReference = EntityFixture.createDocumentReference();
    documentReference.setSourceDocument(null);
    assertThat(documentReference, validateFalse());
  }
}
