package org.documentmanager.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssetDto {
  private Long id;
  private String fileName;
  private String contentType;
  private Long fileSize;
  private String hash;
  private String language;
  private LocalDateTime created;
}
