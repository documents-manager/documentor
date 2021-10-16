package org.documentmanager.entity.dto.search;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;

@Data
@Schema(description = "Sort mapping")
public class SortDto {
    @Schema(description = "The field to sort on", example = "creationDate")
    private String field;
    @Schema(description = "The search order", example = "ASC")
    private SortOrder order = SortOrder.ASC;
}
