package org.documentmanager.entity.dto.document;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class DocumentAutocompleteDto implements Serializable {
    @Schema(type = SchemaType.INTEGER, example = "1000", readOnly = true)
    private Long id;
    @Schema(type = SchemaType.STRING, example = "ABC")
    @NotBlank
    private String title;
}
