package org.documentmanager.entity.dto.document;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

@Data
public class DocumentLinkDto implements Serializable {
    @Schema(type = SchemaType.INTEGER, example = "6")
    private Long id;

    @Schema(type = SchemaType.STRING, example = "Document title")
    private String title;
}
