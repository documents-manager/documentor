package org.documentmanager.boundary;

import io.smallrye.mutiny.Uni;
import org.documentmanager.control.asset.AssetService;
import org.documentmanager.entity.db.Asset;
import org.documentmanager.entity.s3.FormData;
import org.documentmanager.exceptionmapper.S3ExceptionMapper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Path("staging")
@Tag(name = "staging")
public class StagingResource {
    @Inject
    AssetService assetService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
            responseCode = "200",
            description = "ok",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Asset.class)))
    @APIResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Operation(summary = "Upload an asset.", description = "Uploads and returns the created asset.")
    public Uni<Response> uploadFile(
            @Parameter(
                    description = "The page index to retrieve the documents for.",
                    example = "0",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA),
                    schema = @Schema(type = SchemaType.OBJECT, implementation = FormData.class))
            @MultipartForm
            final FormData formData,
            @Parameter(
                    description = "Length of the asset.",
                    example = "56879",
                    schema = @Schema(type = SchemaType.INTEGER))
            @HeaderParam("Content-Length")
            final Long contentLength,
            @Parameter(
                    description = "Language of the asset.",
                    example = "de-DE",
                    schema = @Schema(type = SchemaType.INTEGER))
            @HeaderParam("Accept-Language")
            final String language)
            throws IOException, NoSuchAlgorithmException {
        System.out.println();
        if (formData.getFileName() == null
                || formData.getFileName().isEmpty()
                || formData.getMimeType() == null
                || formData.getMimeType().isEmpty()) {
            return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
        }

        return assetService
                .addStagingAsset(formData, language, contentLength)
                .onItem()
                .transform(asset -> Response.ok(asset).build());
    }

    @DELETE
    @Path("{objectKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "204", description = "ok")
    @APIResponse(
            responseCode = "404",
            description = "Not Found",
            content =
            @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = S3ExceptionMapper.S3Error.class)))
    @Operation(summary = "Delete an asset.", description = "Delete an asset.")
    public Uni<Response> deleteFile(
            @Parameter(
                    description = "Id of the asset.",
                    example = "1234566779",
                    schema = @Schema(type = SchemaType.STRING))
            @PathParam("objectKey")
            final Long objectKey) {
        return assetService
                .deleteAsset(objectKey)
                .onItem()
                .transform(res -> Response.noContent().build());
    }

}
