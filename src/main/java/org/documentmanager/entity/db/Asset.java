package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import lombok.*;
import org.documentmanager.entity.es.LocalDateTimeBridge;
import org.documentmanager.entity.es.MetadataValueBinder;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.PropertyBinderRef;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.ValueBridgeRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyBinding;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
    name = "assetseq",
    sequenceName = "assetseq",
    allocationSize = 1,
    initialValue = 4)
public class Asset extends PanacheEntityBase implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetseq")
  @Schema(type = SchemaType.INTEGER, example = "12345", readOnly = true)
  @GenericField(name = "id")
  private Long id;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "file.pdf", readOnly = true)
  @FullTextField(
          name = "filename_autocomplete",
          analyzer = "autocomplete_indexing",
          searchAnalyzer = "autocomplete_search"
  )
  @GenericField(name = "fileName")
  private String fileName;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "application/pdf", readOnly = true)
  @GenericField(name = "contentType")
  private String contentType;

  @Column(updatable = false)
  @NotNull
  @Schema(type = SchemaType.INTEGER, example = "12345", readOnly = true)
  @GenericField(name = "fileSize")
  private Long fileSize;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "abcdefghaicjldjf", readOnly = true)
  @GenericField(name = "hash")
  private String hash;

  @Column(updatable = false)
  @Schema(type = SchemaType.STRING, example = "de", readOnly = true)
  @GenericField(name = "language")
  private String language;

  @Column(columnDefinition = "TIMESTAMP", updatable = false)
  @CreationTimestamp
  @NotNull
  @Schema(type = SchemaType.STRING, example = "2021-09-23T09:26:26.464Z", readOnly = true)
  @GenericField(name = "created",
          valueBridge = @ValueBridgeRef(type = LocalDateTimeBridge.class))
  private LocalDateTime created;

  @JsonbTransient
  @Column(updatable = false)
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  @FullTextField(analyzer = "german")
  private String ocrContent;

  @JsonbTransient
  @JoinColumn(name = "document_id")
  @ManyToOne(
          cascade = {CascadeType.MERGE}
  )
  @ToString.Exclude
  private Document document;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "asset")
  //@JoinColumn(name = "asset_id")
  @NotNull
  @PropertyBinding(binder = @PropertyBinderRef(type = MetadataValueBinder.class))
  //@IndexedEmbedded
  private Set<Metadata> metadata = new HashSet<>();

  public static List<Asset> findByIds(final Collection<Long> ids) {
    return list("id in :ids", Parameters.with("ids", ids));
  }

  public static List<Asset> findUnassigned() {
    return list("document_id is null");
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    final Asset asset = (Asset) o;

    return Objects.equals(id, asset.id);
  }

  @Override
  public int hashCode() {
    return 469637925;
  }
}
