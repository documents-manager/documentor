package org.documentmanager.boundary;

import org.documentmanager.control.DocumentService;
import org.documentmanager.entity.dto.DocumentDto;
import org.documentmanager.exception.document.DocumentNotFoundException;
import org.documentmanager.mapper.DocumentMapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("documents")
@Produces(APPLICATION_JSON)
public class DocumentResource {
  @Inject DocumentService documentService;

  @Inject DocumentMapper mapper;

  @GET
  @Transactional
  public List<DocumentDto> getAll(
      @QueryParam("page") @DefaultValue("0") final int page,
      @QueryParam("pageSize") @DefaultValue("20") final int pageSize) {
    return documentService
        .list(page, pageSize)
        .map(document -> mapper.toDto(document))
        .collect(Collectors.toList());
  }

  @GET
  @Path("{id}")
  @Transactional
  public DocumentDto getById(@PathParam("id") final Long id) {
    return documentService
        .findByIdOptional(id)
        .map(document -> mapper.toDto(document))
        .orElseThrow(() -> new DocumentNotFoundException(id));
  }

  @Consumes(APPLICATION_JSON)
  @POST
  @Transactional
  public Response create(final DocumentDto documentDto) {
    final var document = mapper.fromDto(documentDto);
    documentService.add(document);
    return Response.created(URI.create("/documents/" + document.getId())).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Consumes(APPLICATION_JSON)
  public DocumentDto updateDocument(@PathParam("id") final Long id, final DocumentDto documentDto) {
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
  public Response deleteById(@PathParam("id") final Long id) {
    documentService.deleteById(id);
    return Response.noContent().build();
  }
}
