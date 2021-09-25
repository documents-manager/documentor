package org.documentmanager.control.s3;

import org.documentmanager.entity.s3.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.model.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class S3RequestFactory {

  @ConfigProperty(name = "bucket.name")
  String bucketName;

  PutObjectRequest createPutRequest(final String objectId, final FormData formData) {
    return PutObjectRequest.builder()
        .bucket(bucketName)
        .key(objectId)
        .contentType(formData.getMimeType())
        .build();
  }

  GetObjectRequest createGetRequest(final String objectKey) {
    return GetObjectRequest.builder().bucket(bucketName).key(objectKey).build();
  }

  DeleteObjectRequest createDeleteRequest(final String objectKey) {
    return DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();
  }

  CreateBucketRequest createPostBucketRequest() {
    return CreateBucketRequest.builder().bucket(bucketName).build();
  }

  public ListObjectsRequest createListRequest() {
    return ListObjectsRequest.builder().bucket(bucketName).build();
  }
}
