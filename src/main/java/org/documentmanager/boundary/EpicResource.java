package org.documentmanager.boundary;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import org.documentmanager.entity.db.Epic;

@ResourceProperties(path = "epics")
public interface EpicResource extends PanacheEntityResource<Epic, Long> {}
