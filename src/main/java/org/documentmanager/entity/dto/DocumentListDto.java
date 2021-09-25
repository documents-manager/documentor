package org.documentmanager.entity.dto;

import lombok.Data;
import org.documentmanager.entity.db.Epic;
import org.documentmanager.entity.db.Label;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DocumentListDto {
  @Schema(type = SchemaType.INTEGER, example = "1000", readOnly = true)
  private Long id;

  @Schema(type = SchemaType.STRING, example = "ABC")
  @NotBlank
  private String title;

  @Schema(type = SchemaType.STRING, example = "Some description")
  private String description;

  @Schema(type = SchemaType.STRING, example = "2021-09-23T09:26:26.464Z", readOnly = true)
  private LocalDateTime created;

  @Schema(type = SchemaType.STRING, example = "2021-09-23T09:26:26.464Z", readOnly = true)
  private LocalDateTime lastUpdated;

  private Epic epic;
  private List<Label> labels;
}
