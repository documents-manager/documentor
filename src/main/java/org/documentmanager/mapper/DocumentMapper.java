package org.documentmanager.mapper;

import org.documentmanager.entity.db.Asset;
import org.documentmanager.entity.db.Document;
import org.documentmanager.entity.db.DocumentReference;
import org.documentmanager.entity.dto.asset.AssetDto;
import org.documentmanager.entity.dto.document.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "cdi",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DocumentMapper {
  DocumentDto toDto(Document document);

  DocumentLinkDto toLinkDto(Document document);

  DocumentListDto toListDto(Document document);

  @Mapping(target = "id", ignore = true)
  Document fromDto(DocumentDto documentDto);

  DocumentReferenceDto toDto(DocumentReference documentReference);

  @Mapping(target = "id", ignore = true)
  void merge(@MappingTarget Document target, Document source);

  AssetDto toAssetDto(Asset asset);

  Asset fromAssetDto(AssetDto assetDto);

  DocumentAutocompleteDto toAutocompleteDto(Document document);
}
