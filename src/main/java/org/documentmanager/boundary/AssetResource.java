package org.documentmanager.boundary;

import io.smallrye.mutiny.Uni;
import org.documentmanager.control.AssetService;
import org.documentmanager.control.s3.S3Service;
import org.documentmanager.entity.s3.FormData;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Path("documents/{id}/assets")
public class AssetResource {

  @Inject S3Service s3Service;

  @Inject AssetService assetService;

  @javax.ws.rs.PathParam("id")
  Long id;

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> uploadFile(
      @MultipartForm final FormData formData,
      @HeaderParam("Content-Length") final Long contentLength,
      @HeaderParam("Accept-Language") final String language)
      throws IOException, NoSuchAlgorithmException {
    if (formData.getFileName() == null
        || formData.getFileName().isEmpty()
        || formData.getMimeType() == null
        || formData.getMimeType().isEmpty()) {
      return Uni.createFrom().item(Response.status(Status.BAD_REQUEST).build());
    }

    return assetService
        .addAssetToDocument(id, formData, language, contentLength)
        .onItem()
        .transform(asset -> Response.ok(asset).build());
  }

  @GET
  @Path("{objectKey}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Uni<Response> downloadFile(@javax.ws.rs.PathParam("objectKey") final String objectKey) {
    return s3Service
        .getObject(objectKey)
        .onItem()
        .transform(
            res -> {
              final var file = res.getFile();
              final var contentType = res.getObject().contentType();
              return Response.ok(file)
                  .header("Content-Disposition", "attachment;filename=" + file.getName())
                  .header("Content-Type", contentType)
                  .build();
            });
  }

  @DELETE
  @Path("{objectKey}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> deleteFile(@PathParam("objectKey") final Long objectKey) {
    return assetService
        .deleteAsset(objectKey)
        .onItem()
        .transform(res -> Response.noContent().build());
  }
}
