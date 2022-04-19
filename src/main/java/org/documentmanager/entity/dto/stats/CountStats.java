package org.documentmanager.entity.dto.stats;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountStats {
    private long documentCount, epicCount, labelCount, assetCount, referenceCount;
}
