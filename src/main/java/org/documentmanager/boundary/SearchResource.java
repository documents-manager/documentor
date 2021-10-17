package org.documentmanager.boundary;

import io.smallrye.mutiny.Uni;
import org.documentmanager.control.search.SearchService;
import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SearchEntityResult;
import org.documentmanager.exceptionmapper.Error;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.Map;

import static org.documentmanager.constants.ExampleResponses.SEARCH_EXAMPLE;

@Path("search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Search")
public class SearchResource {

    @Inject
    SearchService searchService;

    @POST
    @Transactional
    @APIResponse(
            responseCode = "200",
            description = "ok",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Map.class, example = SEARCH_EXAMPLE)))
    @APIResponse(
            responseCode = "404",
            description = "Not found",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema =
                    @Schema(
                            type = SchemaType.OBJECT,
                            implementation = Error.class)))
    @APIResponse(
            responseCode = "400",
            description = "Bad Request",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema =
                    @Schema(
                            type = SchemaType.OBJECT,
                            implementation = Error.class)))
    @Operation(summary = "Search for entities.", description = "Returns a the search result.")
    public Uni<Map<String, SearchEntityResult<? extends Serializable>>> search(final SearchDto search) {
        return searchService.search(search);
    }
}
