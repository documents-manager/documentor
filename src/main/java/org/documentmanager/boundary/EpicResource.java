package org.documentmanager.boundary;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import org.documentmanager.entity.db.Epic;

public interface EpicResource extends PanacheEntityResource<Epic, Long> {}
