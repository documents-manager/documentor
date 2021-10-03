package org.documentmanager;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import io.smallrye.mutiny.Uni;
import org.documentmanager.control.s3.S3RequestFactory;
import org.documentmanager.control.s3.S3Service;
import org.documentmanager.entity.s3.FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import software.amazon.awssdk.services.s3.S3Client;

import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class StartupListener {
    private static final Logger logger = getLogger(StartupListener.class);

    @Transactional
    void startup(
            @Observes final StartupEvent ev,
            @ConfigProperty(name = "bucket.name") final String bucketName,
            final S3Service s3Service,
            final S3Client s3Client,
            final S3RequestFactory builder,
            final SearchSession searchSession) {

        s3Client.listBuckets().buckets().stream()
                .filter(bucket -> bucket.name().equals(bucketName))
                .findFirst()
                .ifPresentOrElse(
                        bucket -> logger.info("Bucket \"{}\" is already present", bucketName),
                        () -> {
                            logger.info("Creating bucket \"{}\"", bucketName);
                            final var request = builder.createPostBucketRequest();
                            s3Client.createBucket(request);
                        });

        // Upload test data in DEV and TEST Mode and index ES
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

                searchSession.scope(Object.class)
                        .massIndexer()
                        .startAndWait();
            } catch (final IOException | InterruptedException e) {
                logger.error("Error initializing dev or test mode", e);
            }
        }
    }
}
