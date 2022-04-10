package org.documentmanager.control.s3;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.documentmanager.entity.s3.FormData;

import java.io.ByteArrayInputStream;
import java.io.File;

public class S3TestFixture {
  private S3TestFixture() {}

  @SneakyThrows
  public static FormData createFormData() {
    final var formData = new FormData();
    formData.setFileName("abc.txt");
    formData.setMimeType("application/text");
    final var inputStream = new ByteArrayInputStream("super-cool-content".getBytes());
    final var file = new File("test");
    FileUtils.copyInputStreamToFile(inputStream, file);
    formData.setData(file);
    return formData;
  }
}
