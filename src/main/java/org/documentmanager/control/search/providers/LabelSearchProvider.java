package org.documentmanager.control.search.providers;

import org.documentmanager.control.search.AutoCompleteProvider;
import org.documentmanager.entity.db.Label;
import org.documentmanager.entity.dto.search.SearchEntityResult;
import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LabelSearchProvider implements AutoCompleteProvider<Label> {
    @Inject
    SearchSession searchSession;

    @Override
    public String type() {
        return "label";
    }

    @Override
    public SearchEntityResult<Label> autocomplete(final String term) {
        final var searchResult = searchSession.search(Label.class)
                .where(f -> f.simpleQueryString().field("name_autocomplete").matching(term))
                .fetch(10);
        return new SearchEntityResult<>(searchResult.hits(), searchResult.total().hitCount());
    }
}
