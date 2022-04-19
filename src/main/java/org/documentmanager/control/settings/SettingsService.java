package org.documentmanager.control.settings;

import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SettingsService {
    @Inject
    SearchSession searchSession;

    public void reindex() throws InterruptedException {
        searchSession.scope(Object.class)
                .massIndexer()
                .startAndWait();
    }
}
