package org.documentmanager.boundary;

import org.documentmanager.control.document.DocumentService;
import org.documentmanager.entity.dto.DocumentDto;
import org.documentmanager.entity.dto.DocumentListDto;
import org.documentmanager.exception.document.DocumentNotFoundException;
import org.documentmanager.exceptionmapper.DocumentExceptionMapper;
import org.documentmanager.mapper.DocumentMapper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("documents")
@Produces(APPLICATION_JSON)
@Tag(name = "Documents")
public final class DocumentResource {
  @Inject DocumentService documentService;

  @Inject DocumentMapper mapper;

  @GET
  @Transactional
  @APIResponse(
      responseCode = "200",
      description = "ok",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema = @Schema(type = SchemaType.ARRAY, implementation = DocumentDto.class)))
  @Operation(summary = "List documents.", description = "Returns the currently stored documents.")
  public List<DocumentListDto> getAll(
      @Parameter(
              description = "The page index to retrieve the documents for.",
              example = "0",
              schema = @Schema(type = SchemaType.INTEGER))
          @QueryParam("page")
          @DefaultValue("0")
          final int page,
      @Parameter(
              description = "The page size to retrieve the documents for.",
              example = "20",
              schema = @Schema(type = SchemaType.INTEGER))
          @QueryParam("pageSize")
          @DefaultValue("20")
          final int pageSize) {
    return documentService
        .list(page, pageSize)
        .map(document -> mapper.toListDto(document))
        .collect(Collectors.toList());
  }

  @GET
  @Path("{id}")
  @Transactional
  @APIResponse(
      responseCode = "200",
      description = "ok",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema = @Schema(type = SchemaType.OBJECT, implementation = DocumentDto.class)))
  @APIResponse(
      responseCode = "404",
      description = "Not found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema =
                  @Schema(
                      type = SchemaType.OBJECT,
                      implementation = DocumentExceptionMapper.DocumentError.class)))
  @Operation(summary = "Get a specific documents.", description = "Returns a specific documents.")
  public DocumentDto getById(
      @Parameter(
              description = "The document id.",
              example = "1234",
              schema = @Schema(type = SchemaType.INTEGER))
          @PathParam("id")
          final Long id) {
    return documentService
        .findByIdOptional(id)
        .map(document -> mapper.toDto(document))
        .orElseThrow(() -> new DocumentNotFoundException(id));
  }

  @Consumes(APPLICATION_JSON)
  @POST
  @Transactional
  @APIResponse(responseCode = "201", description = "ok")
  public Response create(
      @Parameter(
              description = "The document payload.",
              schema = @Schema(type = SchemaType.OBJECT, implementation = DocumentDto.class))
          final DocumentDto documentDto) {
    final var document = mapper.fromDto(documentDto);
    documentService.add(document);
    return Response.created(URI.create("/documents/" + document.getId())).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Consumes(APPLICATION_JSON)
  @APIResponse(responseCode = "204", description = "ok")
  @APIResponse(
      responseCode = "404",
      description = "Not found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema =
                  @Schema(
                      type = SchemaType.OBJECT,
                      implementation = DocumentExceptionMapper.DocumentError.class)))
  @Operation(
      summary = "Updates a specific documents.",
      description = "Updates a specific documents.")
  public DocumentDto updateDocument(
      @Parameter(
              description = "The document id.",
              example = "1234",
              schema = @Schema(type = SchemaType.INTEGER))
          @PathParam("id")
          final Long id,
      @Parameter(
              description = "The document payload.",
              schema = @Schema(type = SchemaType.OBJECT, implementation = DocumentDto.class))
          final DocumentDto documentDto) {
    return documentService
        .findByIdOptional(id)
        .map(
            documentToUpdate -> {
              final var documentUpdated = mapper.fromDto(documentDto);
              mapper.merge(documentToUpdate, documentUpdated);
              return mapper.toDto(documentToUpdate);
            })
        .orElseThrow(() -> new DocumentNotFoundException(documentDto.getId()));
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @APIResponse(responseCode = "204", description = "ok")
  @APIResponse(
      responseCode = "404",
      description = "Not found",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema =
                  @Schema(
                      type = SchemaType.OBJECT,
                      implementation = DocumentExceptionMapper.DocumentError.class)))
  @Operation(
      summary = "Deletes a specific documents.",
      description = "Deletes a specific documents.")
  public Response deleteById(
      @Parameter(
              description = "The document id.",
              example = "1234",
              schema = @Schema(type = SchemaType.INTEGER))
          @PathParam("id")
          final Long id) {
    documentService.deleteById(id);
    return Response.noContent().build();
  }
}
