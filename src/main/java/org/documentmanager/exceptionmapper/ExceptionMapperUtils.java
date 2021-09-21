package org.documentmanager.exceptionmapper;

import javax.ws.rs.core.Response;

public class ExceptionMapperUtils {
    private ExceptionMapperUtils() {
    }

    public static Response buildErrorResponse(Error e, Response.Status status) {
        return Response.status(status)
                .entity(e)
                .build();
    }
}
