package org.documentmanager.control.search;

import java.io.Serializable;

public interface SearchProvider<T extends Serializable, V extends Serializable> extends FullTextProvider<T>, AutoCompleteProvider<V> {
}
