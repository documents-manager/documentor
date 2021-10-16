package org.documentmanager.entity.es;

import org.documentmanager.entity.db.Epic;
import org.hibernate.search.mapper.pojo.bridge.ValueBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeToIndexedValueContext;

public class EpicBridge implements ValueBridge<Epic, String> {
    @Override
    public String toIndexedValue(final Epic epic, final ValueBridgeToIndexedValueContext valueBridgeToIndexedValueContext) {
        if (epic == null) {
            return null;
        }
        return epic.getName();
    }
}
