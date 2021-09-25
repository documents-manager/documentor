package org.documentmanager.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentDto extends DocumentListDto {
  private List<AssetDto> assets;
  private List<DocumentReferenceDto> references;
}
