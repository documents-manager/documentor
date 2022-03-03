package org.documentmanager.control.search.providers;

import org.documentmanager.control.search.AutoCompleteProvider;
import org.documentmanager.entity.db.Epic;
import org.documentmanager.entity.dto.search.SearchEntityResult;
import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EpicSearchProvider implements AutoCompleteProvider<Epic> {
    @Inject
    SearchSession searchSession;

    @Override
    public String type() {
        return "epic";
    }

    @Override
    public SearchEntityResult<Epic> autocomplete(final String term) {
        final var searchResult = searchSession.search(Epic.class)
                .where(f -> f.simpleQueryString().field("name_autocomplete").matching(term))
                .fetch(10);
        return new SearchEntityResult<>(searchResult.hits(), searchResult.total().hitCount());
    }
}
