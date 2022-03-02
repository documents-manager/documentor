package org.documentmanager.control.search;

import org.documentmanager.entity.dto.document.DocumentAutocompleteDto;
import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SearchEntityResult;

import java.io.Serializable;

public interface SearchProvider<T extends Serializable> {
    String type();

    SearchEntityResult<DocumentAutocompleteDto> autocomplete(String term);

    SearchEntityResult<T> search(SearchDto search);
}
