package org.documentmanager.control.search;

import org.documentmanager.entity.dto.search.SearchEntityResult;

import java.io.Serializable;

public interface AutoCompleteProvider<T extends Serializable> {
    String type();

    SearchEntityResult<T> autocomplete(String term);
}
