package org.documentmanager.control.search;

import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SearchEntityResult;

import java.io.Serializable;

public interface SearchProvider<T extends Serializable> {
    String type();

    SearchEntityResult<T> search(SearchDto search);
}
