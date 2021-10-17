package org.documentmanager.entity.dto.asset;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AssetDto implements Serializable {
  private Long id;
  private String fileName;
  private String contentType;
  private Long fileSize;
  private String hash;
  private String language;
  private LocalDateTime created;
}
