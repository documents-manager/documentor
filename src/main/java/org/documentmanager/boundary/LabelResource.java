package org.documentmanager.boundary;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import org.documentmanager.entity.db.Label;

public interface LabelResource extends PanacheEntityResource<Label, Long> {}
