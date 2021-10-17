package org.documentmanager.entity.db;

import org.junit.jupiter.api.Test;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;

class EpicTest {

  @Test
  void checkValidEpic() {
    final var epic = EntityFixture.createEpic();
    assertThat(epic, validateTrue());
  }

  @Test
  void checkInvalidEpicMissingName() {
    final var epic = EntityFixture.createEpic();
    epic.setName(null);
    assertThat(epic, validateFalse());
  }

  @Test
  void checkInvalidEpicBlankName() {
    final var epic = EntityFixture.createEpic();
    epic.setName("");
    assertThat(epic, validateFalse());
  }
}
