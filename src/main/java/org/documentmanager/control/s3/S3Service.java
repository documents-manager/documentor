package org.documentmanager.control.s3;

import io.smallrye.mutiny.Uni;
import org.documentmanager.entity.s3.FileObject;
import org.documentmanager.entity.s3.FormData;
import org.documentmanager.entity.s3.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class S3Service {
  private static final Logger logger = LoggerFactory.getLogger(S3Service.class);
  private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

  @Inject
  S3Client s3;

  @Inject S3RequestFactory requestBuilder;

  public Uni<PutObjectResponse> uploadObject(final String objectKey, final FormData formData) {
    final var request = requestBuilder.createPutRequest(objectKey, formData);
    logger.debug("Uploading file \"{}\" to s3", formData.getFileName());

    return Uni.createFrom()
            .item(
                    () -> {
                      final var file = formData.getData();
                      return s3.putObject(request, RequestBody.fromFile(file));
                    });
  }

  public Uni<GetResponse> getObject(final String objectKey) {
    final var request = requestBuilder.createGetRequest(objectKey);
    final var file = tempFilePath();
    logger.debug("Getting \"{}\" from s3", objectKey);

    return Uni.createFrom()
            .item(() -> s3.getObject(request, ResponseTransformer.toFile(file)))
        .onItem()
        .transform(object -> new GetResponse(object, file));
  }

  public Uni<DeleteObjectResponse> deleteObject(final String objectKey) {
    final var request = requestBuilder.createDeleteRequest(objectKey);
    logger.debug("Deleting \"{}\" from s3", objectKey);
    return Uni.createFrom().item(() -> s3.deleteObject(request));
  }

  public Uni<List<FileObject>> listFiles() {
    final var request = requestBuilder.createListRequest();
    logger.debug("Listing s3 files");

    return Uni.createFrom()
            .item(() -> s3.listObjects(request))
        .onItem()
        .transform(this::toFileItems);
  }

  List<FileObject> toFileItems(final ListObjectsResponse objects) {
    return objects.contents().stream()
        .sorted(Comparator.comparing(S3Object::lastModified).reversed())
        .map(FileObject::from)
        .collect(Collectors.toList());
  }

  File uploadToTemp(final InputStream data) {
    try {
      final File tempPath = File.createTempFile("uploadS3Tmp", ".tmp");
      Files.copy(data, tempPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
      return tempPath;
    } catch (final Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  File tempFilePath() {
    return new File(
        TEMP_DIR,
        "s3AsyncDownloadedTemp" + (new Date()).getTime() + UUID.randomUUID() + "." + ".tmp");
  }
}
