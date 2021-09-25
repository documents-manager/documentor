package org.documentmanager.entity.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class DocumentLinkDto {
  @Schema(type = SchemaType.INTEGER, example = "6")
  private Long id;
  @Schema(type = SchemaType.STRING, example = "Document title")
  private String title;
}
