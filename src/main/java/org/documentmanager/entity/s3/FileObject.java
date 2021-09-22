package org.documentmanager.entity.s3;

import software.amazon.awssdk.services.s3.model.S3Object;

public class FileObject {
  private String objectKey;

  private Long size;

  public static FileObject from(final S3Object s3Object) {
    final var file = new FileObject();
    if (s3Object != null) {
      file.setObjectKey(s3Object.key());
      file.setSize(s3Object.size());
    }
    return file;
  }

  public String getObjectKey() {
    return objectKey;
  }

  public void setObjectKey(final String objectKey) {
    this.objectKey = objectKey;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(final Long size) {
    this.size = size;
  }
}
