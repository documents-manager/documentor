package org.documentmanager.exceptionmapper;

import javax.ws.rs.core.Response;

public final class ExceptionMapperUtils {
  private ExceptionMapperUtils() {}

  public static Response buildErrorResponse(final Error e, final Response.Status status) {
    return Response.status(status).entity(e).build();
  }
}
