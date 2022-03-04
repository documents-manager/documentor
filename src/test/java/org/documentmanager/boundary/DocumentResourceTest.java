package org.documentmanager.boundary;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.documentmanager.testresources.ElasticSearchTestResource;
import org.documentmanager.testresources.MinioTestResource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
@QuarkusTestResource(parallel = true, value = MinioTestResource.class)
@QuarkusTestResource(parallel = true, value = ElasticSearchTestResource.class)
class DocumentResourceTest {

  @Test
  @TestTransaction
  @Order(1)
  void getAll() {
    given()
            .when()
            .get("/documents")
            .then()
            .statusCode(200)
            .log()
            .ifValidationFails();
  }

  @Test
  @TestTransaction
  @Order(2)
  void getById() {
    given()
        .when()
        .get("/documents/1")
        .then()
        .statusCode(200)
        .body("id", is(1))
        .body("title", is(not(emptyOrNullString())))
        .body("description", is(not(emptyOrNullString())))
        .body("created", notNullValue())
        .body("lastUpdated", notNullValue());
  }

  @Test
  @TestTransaction
  @Order(3)
  void create() {
    given()
        .when()
        .log()
        .ifValidationFails()
        .header("Content-Type", MediaType.APPLICATION_JSON)
        .body("{\"title\": \"Aha\", \"description\": \"Take on me\"}")
        .post("/documents")
        .then()
        .statusCode(201);
  }

  @Test
  @TestTransaction
  @Order(4)
  void delete() {
    given().when().delete("/documents/3").then().statusCode(204);
  }

  @Test
  @TestTransaction
  @Order(5)
  void updateDocument() {
    given()
        .when()
        .header("Content-Type", MediaType.APPLICATION_JSON)
        .body("{\"title\": \"Dire Straits\", " + "\"description\": \"Love over gold\"}")
        .put("/documents/1")
        .then()
        .statusCode(200)
        .body("title", is("Dire Straits"))
        .body("description", is("Love over gold"));
  }
}
