package org.documentmanager.control.s3;


import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.documentmanager.entity.s3.FormData;
import software.amazon.awssdk.services.s3.model.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class S3RequestFactory {

    @ConfigProperty(name = "bucket.name")
    String bucketName;

    PutObjectRequest createPutRequest(String objectId, FormData formData) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectId)
                .contentType(formData.getMimeType())
                .build();
    }

    GetObjectRequest createGetRequest(String objectKey) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
    }

    DeleteObjectRequest createDeleteRequest(String objectKey) {
        return DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
    }

    CreateBucketRequest createPostBucketRequest() {
        return CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();
    }

    public ListObjectsRequest createListRequest() {
        return ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
    }
}
