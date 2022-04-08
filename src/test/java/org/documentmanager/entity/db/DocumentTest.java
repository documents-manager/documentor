package org.documentmanager.entity.db;

import org.junit.jupiter.api.Test;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;

class DocumentTest {

  @Test
  void checkValidDocument() {
    final var document = EntityFixture.createBasicDocument();
    assertThat(document, validateTrue());
  }

  @Test
  void checkInvalidDocumentTitle() {
    final var document = EntityFixture.createBasicDocument();
    document.setTitle(null);
    assertThat(document, validateFalse());
  }

  @Test
  void checkInvalidDocumentBlankTitle() {
    final var document = EntityFixture.createBasicDocument();
    document.setTitle("");
    assertThat(document, validateFalse());
  }
}
