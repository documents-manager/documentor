package org.documentmanager.entity.dto.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.documentmanager.entity.db.Epic;
import org.documentmanager.entity.db.Label;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentListDto extends DocumentAutocompleteDto implements Serializable {

    @Schema(type = SchemaType.STRING, example = "Some description")
    private String description;

    @Schema(type = SchemaType.STRING, example = "2021-09-23T09:26:26.464Z", readOnly = true)
    private LocalDateTime created;

    @Schema(type = SchemaType.STRING, example = "2021-09-23T09:26:26.464Z", readOnly = true)
    private LocalDateTime lastUpdated;

    private Epic epic;
    private List<Label> labels = new ArrayList<>();
}
