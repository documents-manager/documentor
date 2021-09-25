package org.documentmanager.control.s3;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.documentmanager.testresources.MinioTestResource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
@QuarkusTestResource(parallel = true, value = MinioTestResource.class)
class S3ServiceTest {
  private static final String OBJECT_KEY = "abc";

  @Inject S3Service service;

  @Test
  @Order(1)
  void uploadObject() {
    final var data = S3TestFixture.createFormData();
    final var res = service.uploadObject(OBJECT_KEY, data).await().indefinitely();

    assertNotNull(res);
  }

  @Test
  @Order(2)
  void getObject() {
    final var res = service.getObject(OBJECT_KEY);

    assertNotNull(res);
  }

  @Test
  @Order(3)
  void listObjects() {
    final var res = service.listFiles().await().indefinitely();

    assertNotNull(res);
    assertFalse(res.isEmpty());
  }

  @Test
  @Order(4)
  void deleteObjects() {
    final var uni = service.deleteObject(OBJECT_KEY).await().indefinitely();
    final var res = service.listFiles().await().indefinitely();

    final var match = res
            .stream()
            .anyMatch(f -> f.getObjectKey().equals(OBJECT_KEY));
    assertFalse(match);
  }
}
