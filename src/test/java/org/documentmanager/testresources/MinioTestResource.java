package org.documentmanager.testresources;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MinioTestResource implements QuarkusTestResourceLifecycleManager {
    private static final Logger logger = getLogger(MinioTestResource.class);
    private static final String REGION = "us-east-1";
    private static final String BUCKET_NAME = "documents";
    private static final String ACCESS_SECRET_KEY = "miniominio";
    private static final int EXPOSED_PORT = 9000;
    private static final String IMAGE_NAME = "minio/minio";
    private static final GenericContainer<?> minio = new GenericContainer<>(IMAGE_NAME)
            .withExposedPorts(EXPOSED_PORT, EXPOSED_PORT)
            .withEnv("MINIO_ACCESS_KEY", ACCESS_SECRET_KEY)
            .withEnv("MINIO_SECRET_KEY", ACCESS_SECRET_KEY)
            .withCommand("server", "/data")
            .waitingFor(Wait.forHttp("/minio/health/ready"))
            .withLogConsumer(new Slf4jLogConsumer(getLogger(MinioTestResource.class)));

    @Override
    public Map<String, String> start() {
        logger.info("Starting container for image: {}", IMAGE_NAME);
        minio.start();
        final var uri = "http://localhost:" + minio.getMappedPort(EXPOSED_PORT);
        S3Client s3 = S3Client
                .builder()
                .endpointOverride(URI.create(uri))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        ACCESS_SECRET_KEY, ACCESS_SECRET_KEY
                )))
                .region(Region.of(REGION))
                .build();

        try {
            s3.createBucket(b -> b.bucket(BUCKET_NAME));
        } catch (BucketAlreadyOwnedByYouException e) {
            logger.info("Bucket {} already present", BUCKET_NAME);
        }

        final var map = new HashMap<String, String>();
        map.put("quarkus.s3.endpoint-override", uri);
        map.put("quarkus.s3.aws.region", REGION);
        map.put("quarkus.s3.aws.credentials.static-provider.access-key-id", ACCESS_SECRET_KEY);
        map.put("quarkus.s3.aws.credentials.static-provider.secret-access-key", ACCESS_SECRET_KEY);
        return map;
    }

    @Override
    public void stop() {
        minio.stop();
        minio.close();
    }
}
