package org.documentmanager.control.factory;

import io.quarkus.tika.TikaMetadata;
import org.documentmanager.control.FileHasher;
import org.documentmanager.control.ocr.OCRReader;
import org.documentmanager.entity.db.Asset;
import org.documentmanager.entity.db.Document;
import org.documentmanager.entity.db.Metadata;
import org.documentmanager.entity.s3.FormData;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AssetFactory {
  @Inject FileHasher fileHasher;

  @Inject OCRReader ocrReader;

  public Asset createAsset(
      final FormData formData, final Document document, final String language, final Long filesize)
      throws NoSuchAlgorithmException, IOException {
    final var parseContent = ocrReader.parse(formData);
    final var languageResult = parseContent.getLanguageResult();
    final var assetLanguage =
        languageResult != null && languageResult.isReasonablyCertain()
            ? languageResult.getLanguage()
            : language;
    final var tikaContent = parseContent.getTikaContent();
    final var ocrContent = tikaContent.getText().trim();
    final var tikaMetadata = tikaContent.getMetadata();
    final var filename = formData.getFileName();
    final var contentType = tikaMetadata.getSingleValue("Content-Type");
    final var metadata = createMetadata(tikaMetadata, assetLanguage);
    final var hash = fileHasher.hashStream(formData.getData());

    return Asset.builder()
        .created(LocalDateTime.now())
        .document(document)
        .fileName(filename)
        .ocrContent(ocrContent)
        .fileSize(filesize)
        .hash(hash)
        .metadata(metadata)
        .contentType(contentType)
        .language(assetLanguage)
        .build();
  }

  Set<Metadata> createMetadata(final TikaMetadata tikaMetadata, final String language) {
    final var metadata = new HashSet<Metadata>();
    if (language != null && !language.isBlank()) {
      final var languageMetadata = createMetadata("language", language);
      metadata.add(languageMetadata);
    }
    for (final String name : tikaMetadata.getNames()) {
      final var value = tikaMetadata.getSingleValue(name);
      if (value != null && !value.isBlank()) {
        final Metadata data = createMetadata(name, value);
        metadata.add(data);
      }
    }

    return metadata;
  }

  Metadata createMetadata(@NotNull final String name, @NotNull final String value) {
    final var data = new Metadata();
    data.setKey(name);
    data.setValue(value.length() > 255 ? value.substring(0, 255) : value);
    return data;
  }
}
