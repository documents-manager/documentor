package org.documentmanager.entity.es;

import org.hibernate.search.mapper.pojo.bridge.ValueBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeToIndexedValueContext;

import java.time.LocalDateTime;

public class LocalDateTimeBridge implements ValueBridge<LocalDateTime, String> {
    @Override
    public String toIndexedValue(LocalDateTime value, ValueBridgeToIndexedValueContext valueBridgeToIndexedValueContext) {
        return value == null ? null : value.toString();
    }
}
