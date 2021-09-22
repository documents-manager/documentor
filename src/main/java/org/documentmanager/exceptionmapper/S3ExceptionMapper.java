package org.documentmanager.exceptionmapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class S3ExceptionMapper implements ExceptionMapper<S3Exception> {

  static S3Error parse(final AwsErrorDetails details) {
    return new S3Error(details);
  }

  @Override
  public Response toResponse(final S3Exception exception) {
    return Response.status(exception.awsErrorDetails().sdkHttpResponse().statusCode())
        .entity(parse(exception.awsErrorDetails()))
        .build();
  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  static class S3Error extends Error {
    private String serviceName;

    @SuppressWarnings("CdiInjectionPointsInspection")
    public S3Error(final AwsErrorDetails details) {
      super(details.errorCode(), details.errorMessage());
      this.serviceName = details.serviceName();
    }
  }
}
