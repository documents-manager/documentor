package org.documentmanager.entity.dto.search;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "Specify the filtering")
public class FilterDto {
    @Schema(description = "terms to match exactly against a property")
    private Map<String, String> term = new HashMap<>();
    @Schema(description = "range to match a property against")
    private Map<String, RangeDto<?>> range = new HashMap<>();
    @Schema(description = "Matches a property against a set of values. Any match")
    private Map<String, List<String>> anyOf = new HashMap<>();
}
