package org.documentmanager.entity.es;

import org.documentmanager.entity.db.Metadata;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexObjectFieldReference;
import org.hibernate.search.engine.backend.document.model.dsl.IndexSchemaElement;
import org.hibernate.search.engine.backend.document.model.dsl.IndexSchemaObjectField;
import org.hibernate.search.mapper.pojo.bridge.PropertyBridge;
import org.hibernate.search.mapper.pojo.bridge.binding.PropertyBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.PropertyBinder;
import org.hibernate.search.mapper.pojo.bridge.runtime.PropertyBridgeWriteContext;

import java.util.Set;

@SuppressWarnings("rawtypes")
public class MetadataValueBinder implements PropertyBinder {

    @Override
    public void bind(final PropertyBindingContext context) {
        context.dependencies()
                .use("key")
                .use("value");

        final IndexSchemaElement schemaElement = context.indexSchemaElement();
        final IndexSchemaObjectField userMetadataField =
                schemaElement.objectField("metadata");

        userMetadataField.fieldTemplate(
                "metadataValueTemplate",
                f -> f.asString().analyzer("english")
        );

        context.bridge(Set.class, new MetadataBridge(userMetadataField.toReference()));
    }

    private static class MetadataBridge implements PropertyBridge<Set> {

        private final IndexObjectFieldReference userMetadataFieldReference;

        MetadataBridge(final IndexObjectFieldReference metadataFieldReference) {
            this.userMetadataFieldReference = metadataFieldReference;
        }

        @Override
        public void write(final DocumentElement target, final Set bridgedElement, final PropertyBridgeWriteContext context) {

            final DocumentElement indexedUserMetadata = target.addObject(userMetadataFieldReference);

            if (bridgedElement == null) {
                return;
            }

            for (final var entry : bridgedElement) {
                final var e = (Metadata) entry;
                final String fieldName = e.getKey().replace(" ", "_");
                final String fieldValue = e.getValue();
                indexedUserMetadata.addValue(fieldName, fieldValue);
            }
        }
    }
}
