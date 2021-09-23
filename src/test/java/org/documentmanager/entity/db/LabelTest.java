package org.documentmanager.entity.db;

import org.junit.jupiter.api.Test;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;

class LabelTest {

  @Test
  void checkValidLabel() {
    final var label = EntityFixture.createLabel();
    assertThat(label, validateTrue());
  }

  @Test
  void checkInvalidLabelMissingName() {
    final var label = EntityFixture.createLabel();
    label.setName(null);
    assertThat(label, validateFalse());
  }

  @Test
  void checkInvalidLabelBlankName() {
    final var label = EntityFixture.createLabel();
    label.setName("");
    assertThat(label, validateFalse());
  }

  @Test
  void checkInvalidMissingVersion() {
    final var label = EntityFixture.createLabel();
    label.setVersion(null);
    assertThat(label, validateFalse());
  }
}
