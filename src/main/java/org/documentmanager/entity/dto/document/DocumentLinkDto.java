package org.documentmanager.entity.dto.document;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;

@Data
public class DocumentLinkDto implements Serializable {
    @Schema(type = SchemaType.INTEGER, example = "6")
    private Long id;

    @Schema(type = SchemaType.STRING, example = "Document title")
    private String title;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DocumentLinkDto that = (DocumentLinkDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
