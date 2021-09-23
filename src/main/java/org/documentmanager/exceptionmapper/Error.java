package org.documentmanager.exceptionmapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Error {
  private String errorCode;
  private String errorMessage;

  public Error(final Response.Status status, final String message) {
    this(status.toString(), message);
  }

  public static Error error(final Response.Status status, final String message) {
    return new Error(status, message);
  }

  public static Error internalError(final String message) {
    return new Error(Response.Status.INTERNAL_SERVER_ERROR, message);
  }

  public static Error internalError() {
    return new Error(Response.Status.INTERNAL_SERVER_ERROR, "Something unusual happened");
  }

  public static Error badRequest(final String message) {
    return new Error(Response.Status.BAD_REQUEST, message);
  }
}
