package org.documentmanager.control.ocr;

import io.quarkus.tika.TikaParser;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageHandler;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.documentmanager.entity.ocr.ParseContent;
import org.documentmanager.entity.s3.FormData;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@ApplicationScoped
public class OCRReader {

  private final TikaParser parser;
  private final OptimaizeLangDetector langDetector;

  public OCRReader(final TikaParser parser) {
    this.parser = parser;
    langDetector = new OptimaizeLangDetector();
    langDetector.loadModels();
  }

  public ParseContent parse(final FormData formData) {
    return parse(formData.getData(), formData.getMimeType());
  }

  public ParseContent parse(final File file, final String contentType) {
    try (final var inputStream = new FileInputStream(file)) {
      final var tikaInputStream = TikaInputStream.get(inputStream);
      final var languageHandler = new LanguageHandler(langDetector);
      final var bodyHandler = new BodyContentHandler();
      final var handler = new TeeContentHandler(languageHandler, bodyHandler);
      final var parse = parser.parse(tikaInputStream, contentType, handler);
      final var languageResult = languageHandler.getLanguage();
      return new ParseContent(parse, bodyHandler.toString(), languageResult);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
