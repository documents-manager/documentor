package org.documentmanager.boundary;

import org.documentmanager.control.settings.SettingsService;
import org.documentmanager.control.stats.StatsService;
import org.documentmanager.mapper.DocumentMapper;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("dashboard")
public class DashboardResource {
    @Inject
    DocumentMapper mapper;

    @Inject
    StatsService statsService;

    @Inject
    SettingsService settingsService;

    @Path("last-updated-documents")
    @GET
    public Response lastUpdatedDocuments(@QueryParam("amount") @DefaultValue("10") int amount) {
        final var documents = statsService.lastUpdatedDocuments(amount)
                .stream()
                .map(doc -> mapper.toListDto(doc))
                .collect(Collectors.toList());
        return Response.ok(documents).build();
    }

    @Path("count")
    @GET
    public Response count() {
        final var countStats = statsService.countStats();
        return Response.ok(countStats).build();
    }

    @Path("unassigned-assets")
    @GET
    public Response unassignedAssets() {
        final var assets = statsService.unassignedAssets()
                .stream()
                .map(asset -> mapper.toAssetDto(asset))
                .collect(Collectors.toList());
        return Response.ok(assets).build();
    }

    @Path("reindex")
    @GET
    public Response reindex() {
        try {
            settingsService.reindex();
            return Response.ok().build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Response.serverError().build();
        }
    }
}
