package org.documentmanager.control.search;

import io.smallrye.mutiny.Uni;
import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SearchEntityResult;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearchService {
    @Inject
    Instance<FullTextProvider<?>> fullTextProviders;

    @Inject
    Instance<AutoCompleteProvider<?>> autoCompleteProviders;


    public Uni<Map<String, SearchEntityResult<? extends Serializable>>> autocomplete(final String term) {

        final var unis = autoCompleteProviders.stream()
                .map(provider -> Uni.createFrom().item(() -> new SearchUniWrapper(provider.type(), provider.autocomplete(term))))
                .collect(Collectors.toList());

        return Uni.combine().all().unis(unis)
                .combinedWith(this::collectToMap);
    }

    public Uni<Map<String, SearchEntityResult<? extends Serializable>>> search(final SearchDto search) {
        final var unis = fullTextProviders.stream()
                .map(provider -> Uni.createFrom().item(() -> new SearchUniWrapper(provider.type(), provider.search(search))))
                .collect(Collectors.toList());

        return Uni.combine().all().unis(unis)
                .combinedWith(this::collectToMap);
    }

    @NotNull
    private Map<String, SearchEntityResult<? extends Serializable>> collectToMap(final List<?> results) {
        return results
                .stream()
                .map(o -> (SearchUniWrapper) o)
                .collect(
                        Collectors.toMap(o -> o.type, o -> o.results)
                );
    }

    public static class SearchUniWrapper {
        public String type;
        public SearchEntityResult<? extends Serializable> results;

        SearchUniWrapper(final String type, final SearchEntityResult<? extends Serializable> results) {
            this.type = type;
            this.results = results;
        }
    }
}
