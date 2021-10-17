package org.documentmanager.entity.dto.search;

import lombok.Data;

@Data
public class RangeDto<T> {
    private T gt;
    private T lt;
}
