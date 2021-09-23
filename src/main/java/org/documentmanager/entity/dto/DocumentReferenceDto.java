package org.documentmanager.entity.dto;

import lombok.Data;
import org.documentmanager.entity.db.DocumentReferenceType;

@Data
public class DocumentReferenceDto {
  private DocumentLinkDto targetDocument;
  private DocumentReferenceType referenceType;
}
