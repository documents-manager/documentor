package org.documentmanager.entity.s3;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormData {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream data;

    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    private String fileName;

    @FormParam("mimetype")
    @PartType(MediaType.TEXT_PLAIN)
    private String mimeType;
}
