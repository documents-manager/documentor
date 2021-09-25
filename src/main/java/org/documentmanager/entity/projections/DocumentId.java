package org.documentmanager.entity.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DocumentId {
  public final Long id;

  public DocumentId(final Long id) {
    this.id = id;
  }
}
