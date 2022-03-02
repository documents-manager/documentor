package org.documentmanager.control.search.providers;

import org.documentmanager.control.search.ElasticSearchFactory;
import org.documentmanager.control.search.SearchProvider;
import org.documentmanager.entity.db.Document;
import org.documentmanager.entity.dto.document.DocumentAutocompleteDto;
import org.documentmanager.entity.dto.document.DocumentListDto;
import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SearchEntityResult;
import org.documentmanager.mapper.DocumentMapper;
import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

@ApplicationScoped
public class DocumentSearchProvider implements SearchProvider<DocumentListDto> {
    @Inject
    SearchSession searchSession;

    @Inject
    ElasticSearchFactory factory;

    @Inject
    DocumentMapper mapper;

    @Override
    public String type() {
        return "documents";
    }

    @Override
    public SearchEntityResult<DocumentAutocompleteDto> autocomplete(final String term) {
        final var searchResult = searchSession.search(Document.class)
                .where(f -> f.simpleQueryString().field("title_autocomplete").matching(term))
                .fetch(10);

        final var docs = searchResult.hits()
                .stream()
                .map(document -> mapper.toAutocompleteDto(document))
                .collect(Collectors.toList());
        return new SearchEntityResult<>(docs, searchResult.total().hitCount());
    }

    @Override
    public SearchEntityResult<DocumentListDto> search(final SearchDto search) {
        final var page = search.getPage();
        final var index = page.getIndex();
        final var size = page.getSize();
        final var offset = Math.max(index - 1, 0) * size;

        final var searchResult = searchSession.search(Document.class)
                .where(f -> factory.createSearchPredicate(search, f))
                .sort(f -> factory.createSortPredicate(search.getSort(), f))
                .fetch(offset, size);

        final var documents = searchResult.hits()
                .stream()
                .map(document -> mapper.toListDto(document))
                .collect(Collectors.toList());

        return new SearchEntityResult<>(documents, searchResult.total().hitCount());
    }
}
