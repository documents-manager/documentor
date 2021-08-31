package org.documentmanager.entity.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.Validator;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void checkInvalidMissingVersion() {
        final var epic = EntityFixture.createEpic();
        epic.setVersion(null);
        assertThat(epic, validateFalse());
    }
}
