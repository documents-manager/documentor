package org.documentmanager.entity.dto.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.documentmanager.entity.dto.asset.AssetDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentDto extends DocumentListDto implements Serializable {
    private List<AssetDto> assets = new ArrayList<>();
    private List<DocumentReferenceDto> references = new ArrayList<>();
    private List<DocumentReferenceDto> referencedBy = new ArrayList<>();
}
