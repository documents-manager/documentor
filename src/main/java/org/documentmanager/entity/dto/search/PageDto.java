package org.documentmanager.entity.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Page spec")
public class PageDto {
    @Schema(description = "Index of the page", example = "1")
    private Integer index;
    @Schema(description = "Page size", example = "20")
    private Integer size;

    public static PageDto of(final Integer index, final Integer size) {
        return new PageDto(index, size);
    }
}
