package org.documentmanager.boundary;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import org.documentmanager.entity.db.Label;

@ResourceProperties(path = "labels")
public interface LabelResource extends PanacheEntityResource<Label, Long> {}
