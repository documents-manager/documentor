package org.documentmanager.entity.s3;

import lombok.Data;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;

@Data
public class GetResponse {
    private GetObjectResponse object;
    private File file;

    public GetResponse(GetObjectResponse object, File file) {
        this.object = object;
        this.file = file;
    }
}
