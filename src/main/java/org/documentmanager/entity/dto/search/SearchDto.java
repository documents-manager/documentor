package org.documentmanager.entity.dto.search;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Search spec")
public class SearchDto {
    @Schema(description = "Query string")
    private String query;
    @Schema(description = "List of sort specs")
    private List<SortDto> sort = new ArrayList<>();
    @Schema(description = "Page")
    private PageDto page = PageDto.of(0, 20);
    @Schema(description = "Filter spec")
    private FilterDto filter;
}
