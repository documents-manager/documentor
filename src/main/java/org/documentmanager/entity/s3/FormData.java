package org.documentmanager.entity.s3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormData {

  @FormParam("file")

  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  private File data;

  @FormParam("filename")
  @PartType(MediaType.TEXT_PLAIN)
  private String fileName;

  @FormParam("mimetype")
  @PartType(MediaType.TEXT_PLAIN)
  private String mimeType;
}
