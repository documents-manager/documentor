package org.documentmanager.entity.db;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(type = SchemaType.STRING, example = "MENTION")
public enum DocumentReferenceType {
  MENTION,
  RELATED
}
