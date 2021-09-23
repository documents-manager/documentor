package org.documentmanager.control.s3;

import org.documentmanager.entity.s3.FormData;

import java.io.ByteArrayInputStream;

public class S3TestFixture {
  private S3TestFixture() {}
  public static FormData createFormData() {
    final var formData = new FormData();
    formData.setFileName("abc.txt");
    formData.setMimeType("application/text");
    formData.setData(new ByteArrayInputStream("super-cool-content".getBytes()));
    return formData;
  }
}
