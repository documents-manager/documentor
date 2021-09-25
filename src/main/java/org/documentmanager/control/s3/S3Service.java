package org.documentmanager.control.s3;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.mutiny.Uni;
import org.documentmanager.entity.s3.FileObject;
import org.documentmanager.entity.s3.FormData;
import org.documentmanager.entity.s3.GetResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
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

  @Inject S3AsyncClient s3;

  @Inject S3RequestFactory requestBuilder;

  public Uni<PutObjectResponse> uploadObject(final String objectKey, final FormData formData) {
    final var request = requestBuilder.createPutRequest(objectKey, formData);
    logger.debug("Uploading file \"{}\" to s3", formData.getFileName());

    return Uni.createFrom()
        .completionStage(
            () ->
                s3.putObject(request, AsyncRequestBody.fromFile(uploadToTemp(formData.getData()))));
  }

  public Uni<GetResponse> getObject(final String objectKey) {
    final var request = requestBuilder.createGetRequest(objectKey);
    final var file = tempFilePath();
    logger.debug("Getting \"{}\" from s3", objectKey);

    return Uni.createFrom()
        .completionStage(() -> s3.getObject(request, AsyncResponseTransformer.toFile(file)))
        .onItem()
        .transform(object -> new GetResponse(object, file));
  }

  public Uni<DeleteObjectResponse> deleteObject(final String objectKey) {
    final var request = requestBuilder.createDeleteRequest(objectKey);
    logger.debug("Deleting \"{}\" from s3", objectKey);
    return Uni.createFrom().completionStage(() -> s3.deleteObject(request));
  }

  public Uni<List<FileObject>> listFiles() {
    final var request = requestBuilder.createListRequest();
    logger.debug("Listing s3 files");

    return Uni.createFrom()
        .completionStage(() -> s3.listObjects(request))
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

  void startup(
      @Observes final StartupEvent ev,
      @ConfigProperty(name = "bucket.name") final String bucketName,
      final S3Service s3Service,
      final S3Client s3Client,
      final S3RequestFactory builder) {

    s3Client.listBuckets().buckets().stream()
        .filter(bucket -> bucket.name().equals(bucketName))
        .findFirst()
        .ifPresentOrElse(
            bucket -> logger.info("Bucket \"{}\" is already present", bucketName),
            () -> {
              logger.info("Creating bucket \"{}\"", bucketName);
              final var request = builder.createPostBucketRequest();
              s3.createBucket(request);
            });

    // Upload test data in DEV and TEST Mode
    final var profile = ProfileManager.getActiveProfile();
    if ("dev".equals(profile) || "test".equals(profile)) {
      try (final var in1 = getClass().getResourceAsStream("/test-files/asset1.pdf");
          final var in2 = getClass().getResourceAsStream("/test-files/asset2.jpg")) {
        final var data1 = new FormData(in1, "asset1.pdf", "application/pdf");
        final var data2 = new FormData(in2, "asset2.jpg", "image/jpg");
        Uni.combine()
            .all()
            .unis(s3Service.uploadObject("1", data1), s3Service.uploadObject("2", data2))
            .asTuple()
            .onItem()
            .invoke(() -> logger.info("Uploaded test file to s3"))
            .onFailure()
            .invoke(() -> logger.info("Test files already present"))
            .await()
            .indefinitely();
      } catch (final IOException e) {
        logger.error("Error initializing test", e);
      }
    }
  }
}
