package org.documentmanager.exceptionmapper;

import org.hibernate.search.util.common.SearchException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.documentmanager.exceptionmapper.Error.badRequest;


@Provider
public class SearchExceptionMapper implements ExceptionMapper<SearchException> {
    @Override
    public Response toResponse(final SearchException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(badRequest(e.getMessage()))
                .build();
    }
}
