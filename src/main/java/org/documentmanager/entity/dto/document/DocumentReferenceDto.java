package org.documentmanager.entity.dto.document;

import lombok.Data;
import org.documentmanager.entity.db.DocumentReferenceType;

import java.io.Serializable;

@Data
public class DocumentReferenceDto implements Serializable {
  private DocumentLinkDto targetDocument;
  private DocumentReferenceType referenceType;
}
