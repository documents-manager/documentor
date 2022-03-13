package org.documentmanager.entity.db;

import org.junit.jupiter.api.Test;

import static org.documentmanager.matcher.ValidatorFalseMatcher.validateFalse;
import static org.documentmanager.matcher.ValidatorTrueMatcher.validateTrue;
import static org.hamcrest.MatcherAssert.assertThat;

class AssetTest {

  @Test
  void checkValidAsset() {
    final var asset = EntityFixture.createAssetWithDocument();
    assertThat(asset, validateTrue());
  }

  @Test
  void checkInvalidAssetWithoutDocument() {
    final var asset = EntityFixture.createAsset();
    assertThat(asset, validateTrue());
  }

  @Test
  void checkInvalidAssetMissingHash() {
    final var asset = EntityFixture.createAsset();
    asset.setHash(null);
    assertThat(asset, validateFalse());
  }

  @Test
  void checkInvalidAssetBlankHash() {
    final var asset = EntityFixture.createAsset();
    asset.setHash("");
    assertThat(asset, validateFalse());
  }

  @Test
  void checkInvalidAssetMissingFileName() {
    final var asset = EntityFixture.createAsset();
    asset.setFileName(null);
    assertThat(asset, validateFalse());
  }

  @Test
  void checkInvalidAssetBlankFilename() {
    final var asset = EntityFixture.createAsset();
    asset.setFileName("");
    assertThat(asset, validateFalse());
  }

  @Test
  void checkInvalidAssetMissingMimetype() {
    final var asset = EntityFixture.createAsset();
    asset.setContentType(null);
    assertThat(asset, validateFalse());
  }

  @Test
  void checkInvalidAssetBlankMimetype() {
    final var asset = EntityFixture.createAsset();
    asset.setContentType("");
    assertThat(asset, validateFalse());
  }

  @Test
  void checkInvalidAssetFilesize() {
    final var asset = EntityFixture.createAsset();
    asset.setFileSize(null);
    assertThat(asset, validateFalse());
  }
}
