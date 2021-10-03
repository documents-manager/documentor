package org.documentmanager.control.ocr;

import io.quarkus.tika.TikaParser;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageHandler;
import org.apache.tika.sax.BodyContentHandler;
import org.documentmanager.entity.ocr.ParseContent;
import org.documentmanager.entity.s3.FormData;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class OCRReader {

  private final TikaParser parser;
  private final OptimaizeLangDetector langDetector;

  public OCRReader(final TikaParser parser) {
    this.parser = parser;
    langDetector = new OptimaizeLangDetector();
    langDetector.loadModels();
  }

  public ParseContent parse(final FormData formData) throws IOException {
    return parse(formData.getData(), formData.getMimeType());
  }

  public ParseContent parse(final InputStream inputStream, final String contentType) {
    final var tikaInputStream = TikaInputStream.get(inputStream);
    final var languageHandler = new LanguageHandler(langDetector);
    final var handler = new BodyContentHandler(languageHandler);
    final var parse = parser.parse(tikaInputStream, contentType, handler);
    final var languageResult = languageHandler.getLanguage();
    return new ParseContent(parse, languageResult);
  }
}
