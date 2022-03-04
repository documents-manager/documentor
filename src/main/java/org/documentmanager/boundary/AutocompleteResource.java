package org.documentmanager.boundary;

import io.smallrye.mutiny.Uni;
import org.documentmanager.control.search.SearchService;
import org.documentmanager.entity.dto.search.SearchEntityResult;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.Serializable;
import java.util.Map;

@Path("autocomplete")
@ApplicationScoped
public class AutocompleteResource {
    @Inject
    SearchService searchService;

    @GET
    public Uni<Map<String, SearchEntityResult<? extends Serializable>>> autocomplete(@QueryParam("q") final String term) {
        return searchService.autocomplete(term);
    }
}
