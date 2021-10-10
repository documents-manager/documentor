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
    public void configure(ElasticsearchAnalysisConfigurationContext context) {
        context.analyzer("name").custom()
                .tokenizer("standard")
                .tokenFilters(ASCII_FOLDING, LOWERCASE);

        context.analyzer("german").custom()
                .tokenizer("standard")
                .tokenFilters(ASCII_FOLDING, LOWERCASE, "porter_stem");

        context.normalizer("sort").custom()
                .tokenFilters(ASCII_FOLDING, LOWERCASE);
    }
}
