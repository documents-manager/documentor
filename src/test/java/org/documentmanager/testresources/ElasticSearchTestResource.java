package org.documentmanager.testresources;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

public final class ElasticSearchTestResource implements QuarkusTestResourceLifecycleManager {

    static final DockerImageName ES_OSS_IMAGE_NAME = DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch-oss:7.10.2");
    static final ElasticsearchContainer container = new ElasticsearchContainer(ES_OSS_IMAGE_NAME);

    @Override
    public Map<String, String> start() {
        container.start();
        final var map = new HashMap<String, String>();
        map.put("quarkus.hibernate-search-orm.elasticsearch.version", "7");
        map.put("quarkus.hibernate-search-orm.elasticsearch.hosts", container.getHttpHostAddress());
        map.put("quarkus.hibernate-search-orm.schema-management.strategy", "drop-and-create-and-drop");
        return map;
    }

    @Override
    public void stop() {
        container.stop();
    }
}
