package org.documentmanager.entity.dto.document;

import lombok.Data;
import org.documentmanager.entity.db.DocumentReferenceType;

import java.io.Serializable;
import java.util.Objects;

@Data
public class DocumentReferenceDto implements Serializable {
  private DocumentLinkDto sourceDocument;
  private DocumentLinkDto targetDocument;
  private DocumentReferenceType referenceType;

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final DocumentReferenceDto that = (DocumentReferenceDto) o;
    return Objects.equals(targetDocument, that.targetDocument) && referenceType == that.referenceType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetDocument, referenceType);
  }
}
