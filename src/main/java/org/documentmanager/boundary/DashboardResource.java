package org.documentmanager.boundary;

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

    @Path("last-updated")
    @GET
    public Response lastUpdated(@QueryParam("amount") @DefaultValue("10") int amount) {
        final var documents = statsService.lastUpdated(amount)
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
}
