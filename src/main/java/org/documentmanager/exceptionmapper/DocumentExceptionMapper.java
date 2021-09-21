package org.documentmanager.exceptionmapper;

import org.documentmanager.exception.document.DocumentException;
import org.documentmanager.exception.document.DocumentNotDeletableException;
import org.documentmanager.exception.document.DocumentNotFoundException;
import org.documentmanager.exception.document.UnsupportedDocumentException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.documentmanager.exceptionmapper.ExceptionMapperUtils.buildErrorResponse;

@Provider
public class DocumentExceptionMapper implements ExceptionMapper<DocumentException> {
    @Override
    public Response toResponse(DocumentException e) {
        if (UnsupportedDocumentException.class.equals(e.getClass())) {
            return buildErrorResponse(new DocumentError(Response.Status.NOT_ACCEPTABLE, e.getMessage()), Response.Status.NOT_ACCEPTABLE);
        }

        if (DocumentNotFoundException.class.equals(e.getClass())) {
            return buildErrorResponse(new DocumentError(Response.Status.NOT_FOUND, e.getMessage()), Response.Status.NOT_FOUND);
        }

        if (DocumentNotDeletableException.class.equals(e.getClass())) {
            return buildErrorResponse(new DocumentError(Response.Status.BAD_REQUEST, e.getMessage()), Response.Status.NOT_FOUND);
        }
        return Response
                .serverError()
                .entity(buildErrorResponse(
                        new DocumentError(Response.Status.INTERNAL_SERVER_ERROR,
                                "Something usual happened"),
                        Response.Status.INTERNAL_SERVER_ERROR))
                .build();
    }

    static class DocumentError extends Error {
        public DocumentError(String errorCode, String errorMessage) {
            super(errorCode, errorMessage);
        }

        public DocumentError(Response.Status status, String message) {
            super(status, message);
        }
    }
}
