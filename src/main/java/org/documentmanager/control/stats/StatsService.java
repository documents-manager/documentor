package org.documentmanager.control.stats;

import org.documentmanager.entity.db.*;
import org.documentmanager.entity.dto.stats.CountStats;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StatsService {
    public List<Document> lastUpdatedDocuments(int amount) {
        return Document.lastUpdated(amount);
    }

    public CountStats countStats() {
        final var docCount = Document.count();
        final var assetCount = Asset.count();
        final var epicCount = Epic.count();
        final var labelCount = Label.count();
        final var referenceCount = DocumentReference.count();

        return CountStats.builder()
                .assetCount(assetCount)
                .documentCount(docCount)
                .epicCount(epicCount)
                .labelCount(labelCount)
                .referenceCount(referenceCount)
                .build();
    }

    public List<Asset> unassignedAssets() {
        return Asset.findUnassigned();
    }

    public List<Document> lastCreatedDocuments(final int amount) {
        return Document.lastCreated(amount);
    }
}
