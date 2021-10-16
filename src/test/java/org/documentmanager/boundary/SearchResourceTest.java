package org.documentmanager.boundary;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.documentmanager.entity.dto.search.FilterDto;
import org.documentmanager.entity.dto.search.SearchDto;
import org.documentmanager.entity.dto.search.SortDto;
import org.documentmanager.testresources.ElasticSearchTestResource;
import org.documentmanager.testresources.MinioTestResource;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
@QuarkusTestResource(parallel = true, value = MinioTestResource.class)
@QuarkusTestResource(parallel = true, value = ElasticSearchTestResource.class)
class SearchResourceTest {

    @Test
    void searchWithQuery() {
        final var searchDto = new SearchDto();
        searchDto.setQuery("Stupid");
        given()
                .log()
                .ifValidationFails()
                .body(searchDto)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post("/search")
                .then()
                .body("documents.data.size()", equalTo(3))
                .statusCode(200);
    }

    @Test
    void searchWithQueryAndFilter() {
        final var searchDto = new SearchDto();
        final var filterDto = new FilterDto();
        filterDto.setTerm(Collections.singletonMap("assets.metadata.access_permission:can_print", "true"));
        searchDto.setQuery("Stupid");
        given()
                .log()
                .ifValidationFails()
                .body(searchDto)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post("/search")
                .then()
                .body("documents.data.size()", equalTo(1))
                .statusCode(200);
    }

    @Test
    void searchWithQueryAndSort() {
        final var searchDto = new SearchDto();
        final var sortDto = new SortDto();
        sortDto.setField("title");
        sortDto.setOrder(SortOrder.DESC);
        searchDto.setQuery("Stupid");
        given()
                .log()
                .ifValidationFails()
                .body(searchDto)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post("/search")
                .then()
                .body("documents.data.size()", equalTo(3))
                .body("documents.data[1].title", equalTo("Document 3"))
                .statusCode(200);
    }
}
