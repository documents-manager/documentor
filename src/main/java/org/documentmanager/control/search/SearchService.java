package org.documentmanager.control.search;

import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SearchEntityResult;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearchService {
    @Inject
    Instance<SearchProvider<?>> searchProviders;

    @Transactional
    public Map<String, SearchEntityResult<? extends Serializable>> search(final SearchDto search) {
        return searchProviders
                .stream()
                .parallel()
                .collect(
                        Collectors.toMap(
                                SearchProvider::type,
                                provider -> provider.search(search)
                        )
                );
    }
}
