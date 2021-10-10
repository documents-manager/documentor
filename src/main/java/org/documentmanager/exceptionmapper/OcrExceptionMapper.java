package org.documentmanager.exceptionmapper;

import org.documentmanager.exception.ocr.OcrException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.documentmanager.exceptionmapper.Error.badRequest;


@Provider
public class OcrExceptionMapper implements ExceptionMapper<OcrException> {
    @Override
    public Response toResponse(final OcrException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(badRequest(e.getMessage()))
                .build();
    }
}
