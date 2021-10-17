package org.documentmanager.entity.dto.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.documentmanager.entity.dto.asset.AssetDto;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentDto extends DocumentListDto implements Serializable {
    private List<AssetDto> assets;
    private List<DocumentReferenceDto> references;
}
