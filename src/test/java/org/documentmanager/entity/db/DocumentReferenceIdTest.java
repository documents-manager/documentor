package org.documentmanager.entity.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.Validator;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
