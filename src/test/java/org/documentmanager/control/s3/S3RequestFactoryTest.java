package org.documentmanager.control.s3;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.documentmanager.testresources.MinioTestResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(parallel = true, value = MinioTestResource.class)
class S3RequestFactoryTest {
  @Inject S3RequestFactory factory;

  @BeforeEach
  void setUp() {
    factory.bucketName = "documents";
  }

  @Test
  void createPutRequest() {
    final var data = S3TestFixture.createFormData();
    final var request = factory.createPutRequest("abc", data);
    assertEquals("application/text", request.contentType());
    assertEquals("documents", request.bucket());
    assertEquals("abc", request.key());
  }

  @Test
  void createGetRequest() {
    final var data = S3TestFixture.createFormData();
    final var request = factory.createGetRequest("abc");
    assertEquals("documents", request.bucket());
    assertEquals("abc", request.key());
  }

  @Test
  void createDeleteRequest() {
    final var data = S3TestFixture.createFormData();
    final var request = factory.createDeleteRequest("abc");
    assertEquals("documents", request.bucket());
    assertEquals("abc", request.key());
  }

  @Test
  void createPostBucketRequest() {
    final var request = factory.createPostBucketRequest();
    assertEquals("documents", request.bucket());
  }

  @Test
  void createListRequest() {
    final var request = factory.createListRequest();
    assertEquals("documents", request.bucket());
  }
}
