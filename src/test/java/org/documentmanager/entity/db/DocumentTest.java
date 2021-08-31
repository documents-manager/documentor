package org.documentmanager.entity.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.Validator;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    void checkValidDocument() {
        final var document = EntityFixture.createBasicDocument();
        assertThat(document, validateTrue());
    }

    @Test
    void checkInvalidDocumentMissingVersion() {
        final var document = EntityFixture.createBasicDocument();
        document.setVersion(null);
        assertThat(document, validateFalse());
    }

    @Test
    void checkInvalidDocumentMissingUpdated() {
        final var document = EntityFixture.createBasicDocument();
        document.setLastUpdated(null);
        assertThat(document, validateFalse());
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
