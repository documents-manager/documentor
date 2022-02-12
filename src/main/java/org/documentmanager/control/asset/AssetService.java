package org.documentmanager.control.asset;

import io.smallrye.mutiny.Uni;
import org.documentmanager.control.asset.AssetFactory;
import org.documentmanager.control.s3.S3Service;
import org.documentmanager.entity.db.Asset;
import org.documentmanager.entity.db.Document;
import org.documentmanager.entity.s3.FormData;
import org.documentmanager.exception.document.DocumentNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class AssetService {

    @Inject
    S3Service s3;

    @Inject
    AssetFactory factory;

    @Transactional
    public Uni<Asset> addAssetToDocument(
            final Long documentId, final FormData formData, final String language, final Long fileSize)
            throws IOException, NoSuchAlgorithmException {
        final var document =
                (Document)
                        Document.findByIdOptional(documentId)
                                .orElseThrow(() -> new DocumentNotFoundException(documentId));

        return addAsset(formData, language, fileSize, document);
    }

    private Uni<Asset> addAsset(final FormData formData, final String language, final Long fileSize, final Document document) throws NoSuchAlgorithmException, IOException {
        final var asset = factory.createAsset(formData, document, language, fileSize);
        asset.persist();
        if (document != null) {
            document.getAssets().add(asset);
        }

        return s3.uploadObject(asset.getId().toString(), formData)
                .onItem()
                .transform(putObjectResponse -> asset);
    }

    @Transactional
    public Uni<Asset> addStagingAsset(final FormData formData, final String language, final Long fileSize) throws NoSuchAlgorithmException, IOException {
        return addAsset(formData, language, fileSize, null);
    }

    @Transactional
    public Uni<Asset> deleteAsset(final Long objectId) {
        return Uni.createFrom()
                .item(
                        () -> {
                            final var asset = (Asset) Asset.findById(objectId);
                            asset.delete();
                            return asset;
                        })
                .onItem()
                .transformToUni(
                        asset -> s3.deleteObject(objectId.toString()).onItem().transform(ignored -> asset));
    }
}
