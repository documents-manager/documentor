package org.documentmanager.boundary;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.documentmanager.testresources.ElasticSearchTestResource;
import org.documentmanager.testresources.MinioTestResource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
@QuarkusTestResource(parallel = true, value = MinioTestResource.class)
@QuarkusTestResource(parallel = true, value = ElasticSearchTestResource.class)
class AssetResourceTest {

  @Test
  @Order(1)
  void uploadFile() throws IOException {
    try (final InputStream inputStream = getClass().getResourceAsStream("/index.html")) {
      given()
          .formParam("filename", "index.html")
          .formParam("mimetype", "text/html")
          .multiPart("file", inputStream, "text/html")
          .when()
          .post("documents/3/assets")
          .then()
          .statusCode(200)
          .log()
          .ifValidationFails()
          .extract()
          .body()
          .path("id");
    }
  }

  @Test
  @Order(2)
  void downloadFile() {
    final var byteArray =
        given()
            .get("documents/1/assets/1")
            .then()
            .log()
            .ifValidationFails()
            .header("Content-Type", "application/pdf")
            .header("Content-Disposition", matchesPattern("attachment;filename=.+"))
            .extract()
            .asByteArray();

    assertEquals(9243, byteArray.length);
  }

  @Test
  @Order(3)
  void deleteFile() {
    given().delete("documents/1/assets/1").then().statusCode(204);
  }
}
