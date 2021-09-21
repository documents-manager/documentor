package org.documentmanager.entity.dto;

import lombok.Data;
import org.documentmanager.entity.db.Asset;
import org.documentmanager.entity.db.Epic;
import org.documentmanager.entity.db.Label;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DocumentDto {
    private Long id;
    @NotBlank
    private String title;
    private String description;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
    private Epic epic;
    private List<Label> labels;
    private List<Asset> assets;
    private List<DocumentReferenceDto> references;
}
