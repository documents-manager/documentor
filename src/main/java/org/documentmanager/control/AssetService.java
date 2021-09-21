package org.documentmanager.control;


import io.smallrye.mutiny.Uni;
import org.documentmanager.control.s3.S3Service;
import org.documentmanager.entity.db.Asset;
import org.documentmanager.entity.db.Document;
import org.documentmanager.entity.s3.FormData;
import org.documentmanager.exception.document.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@ApplicationScoped
public class AssetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetService.class);

    @Inject
    S3Service s3;

    @Inject
    FileHasher hasher;

    @Transactional
    public Uni<Asset> addAssetToDocument(Long documentId, FormData formData, String language, Long fileSize) throws IOException, NoSuchAlgorithmException {
        final var document = (Document) Document.findByIdOptional(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(documentId));

        final String fileHash = hasher.hashStream(formData.getData());

        return Uni.createFrom()
                .item(() -> {
                    String ocrContent = getOcrContent(formData, language);
                    final var asset = Asset.builder()
                            .created(LocalDateTime.now())
                            .document(document)
                            .fileName(formData.getFileName())
                            .ocrContent(ocrContent)
                            .fileSize(fileSize)
                            .hash(fileHash)
                            .mimeType(formData.getMimeType())
                            .build();
                    asset.persist();
                    document.getAssets().add(asset);
                    return asset;
                })
                .onItem()
                .transformToUni(asset ->
                        s3.uploadObject(asset.getId().toString(), formData)
                                .onItem()
                                .transform(putObjectResponse -> asset)
                );
    }

    @Transactional
    public Uni<Asset> deleteAsset(Long objectId) {
        return Uni.createFrom().item(() -> {
                    final var asset = (Asset) Asset.findById(objectId);
                    asset.delete();
                    return asset;
                })
                .onItem()
                .transformToUni(asset -> s3.deleteObject(objectId.toString())
                        .onItem()
                        .transform(ignored -> asset));
    }

    String getOcrContent(FormData formData, String language) {
        //TODO Implement later
        return null;
    }

}
