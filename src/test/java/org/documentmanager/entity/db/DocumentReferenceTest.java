package org.documentmanager.entity.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.Validator;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
