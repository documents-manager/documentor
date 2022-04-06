package org.documentmanager.entity.dto.asset;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class AssetDto implements Serializable {
  private Long id;
  private String fileName;
  private String contentType;
  private Long fileSize;
  private String hash;
  private String language;
  private LocalDateTime created;

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final AssetDto assetDto = (AssetDto) o;
    return Objects.equals(id, assetDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
