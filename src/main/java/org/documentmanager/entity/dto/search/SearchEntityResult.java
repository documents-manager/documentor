package org.documentmanager.entity.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.documentmanager.entity.dto.document.DocumentListDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Describes hits for an entity")
public class SearchEntityResult<T> {
    @Schema(description = "List of hits", implementation = DocumentListDto.class)
    private List<T> hits;
    @Schema(description = "Count of hits")
    private Long hitCount;
}
