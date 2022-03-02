package org.documentmanager.entity.es;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named("documentAnalysisConfigurer")
public class AnalysisConfigurer implements ElasticsearchAnalysisConfigurer {

    private static final String ASCII_FOLDING = "asciifolding";
    private static final String LOWERCASE = "lowercase";

    @Override
    public void configure(final ElasticsearchAnalysisConfigurationContext context) {
        context.analyzer("name").custom()
                .tokenizer("standard")
                .tokenFilters(ASCII_FOLDING, LOWERCASE);

        context.analyzer("german").custom()
                .tokenizer("standard")
                .tokenFilters(ASCII_FOLDING, LOWERCASE, "porter_stem");

        context.analyzer("autocomplete_indexing").custom()
                .tokenizer("whitespace")
                .tokenFilters("lowercase", "asciifolding", "autocomplete_edge_ngram");
        context.tokenFilter("autocomplete_edge_ngram")
                .type("edge_ngram")
                .param("min_gram", 1)
                .param("max_gram", 10);
        // Same as "autocomplete_indexing", but without the edge-ngram filter
        context.analyzer("autocomplete_search").custom()
                .tokenizer("whitespace")
                .tokenFilters("lowercase", "asciifolding");

        context.normalizer("sort").custom()
                .tokenFilters(ASCII_FOLDING, LOWERCASE);
    }
}
