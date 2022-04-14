package org.documentmanager.control.stats;

import org.documentmanager.entity.db.Document;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StatsService {
    public List<Document> lastUpdated(int amount) {
        return Document.lastUpdated(amount);
    }
}
