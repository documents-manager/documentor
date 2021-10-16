package org.documentmanager.control.search;

import org.apache.commons.lang3.function.TriFunction;
import org.documentmanager.entity.dto.search.FilterDto;
import org.documentmanager.entity.dto.search.RangeDto;
import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SortDto;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.PredicateFinalStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.sort.dsl.SearchSortFactory;
import org.hibernate.search.engine.search.sort.dsl.SortFinalStep;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class ElasticSearchFactory {
    @SuppressWarnings("")
    public BooleanPredicateClausesStep<?> createSearchPredicate(final SearchDto search, final SearchPredicateFactory f) {
        final var query = search.getQuery();
        final PredicateFinalStep queryPredicate = createQueryPredicate(f, query);

        var q = f.bool().filter(queryPredicate);

        if (search.getFilter() != null) {
            final var filterPredicates = createFilterPredicates(search.getFilter(), f);

            for (final var filterPredicate : filterPredicates) {
                q = q.must(filterPredicate);
            }
        }

        return q;
    }

    public SortFinalStep createSortPredicate(final List<SortDto> sorts, final SearchSortFactory f) {
        if (sorts == null || sorts.isEmpty()) {
            return f.score();
        }

        final var composite = f.composite();
        for (final var sort : sorts) {
            final var fieldName = String.format("%s_sort", sort.getField());
            composite.add(f.field(fieldName).order(sort.getOrder()));
        }

        return composite;
    }

    PredicateFinalStep createQueryPredicate(final SearchPredicateFactory f, final String query) {
        return query == null || query.trim().isEmpty() ?
                f.matchAll() :
                f.simpleQueryString()
                        .fields("description", "title", "assets.ocrContent")
                        .matching(query);
    }

    List<PredicateFinalStep> createFilterPredicates(final FilterDto filter, final SearchPredicateFactory f) {
        final var termPredicates = createTermPredicates(filter.getTerm(), f);
        final var rangePredicates = createRangePredicates(filter.getRange(), f);
        final var anyOffPredicates = createAnyOfPredicates(filter.getAnyOf(), f);

        return Stream.of(termPredicates, rangePredicates, anyOffPredicates)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    List<PredicateFinalStep> createAnyOfPredicates(final Map<String, List<String>> anyOf, final SearchPredicateFactory f) {
        return buildPredicateWithFunction(anyOf, f, (key, values, factory) -> {
                    var q = f.bool();
                    for (final var value : values) {
                        q = q.should(f.match().fields(key).matching(value));
                    }
                    return q;
                }
        );
    }

    List<PredicateFinalStep> createRangePredicates(final Map<String, RangeDto<?>> range, final SearchPredicateFactory f) {
        return buildPredicateWithFunction(range, f, (key, value, factory) -> {
                    if (key == null || value == null) {
                        return null;
                    }
                    final var field = f.range().fields(key);
                    if (value.getGt() != null && value.getLt() != null) {
                        return field.between(value.getGt(), value.getLt());
                    }
                    if (value.getGt() != null) {
                        return field.greaterThan(value.getGt());
                    }
                    if (value.getLt() != null) {
                        return field.greaterThan(value.getLt());
                    }
                    return null;
                }
        );
    }

    List<PredicateFinalStep> createTermPredicates(final Map<String, String> term, final SearchPredicateFactory f) {
        return buildPredicateWithFunction(term, f, (key, value, factory) ->
                value == null || value.trim().isEmpty() || key == null || key.trim().isEmpty() ?
                        f.matchAll() :
                        f.simpleQueryString()
                                .fields(key)
                                .matching(value)
        );
    }

    @NotNull
    <T> List<PredicateFinalStep> buildPredicateWithFunction(
            final Map<String, T> base,
            final SearchPredicateFactory f, final TriFunction<String, T, SearchPredicateFactory, PredicateFinalStep> fn
    ) {
        return base.entrySet()
                .stream()
                .map(entry -> {
                    final var key = entry.getKey();
                    final var value = entry.getValue();
                    return fn.apply(key, value, f);
                })
                .collect(Collectors.toList());
    }
}
