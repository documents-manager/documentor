package org.documentmanager.entity.ocr;

import io.quarkus.tika.TikaContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tika.language.detect.LanguageResult;

@Getter
@AllArgsConstructor
public class ParseContent {
  private final TikaContent tikaContent;
  private final LanguageResult languageResult;
}
