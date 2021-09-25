package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
    name = "assetseq",
    sequenceName = "assetseq",
    allocationSize = 1,
    initialValue = 4)
public final class Asset extends PanacheEntityBase implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetseq")
  @Schema(type = SchemaType.INTEGER, example = "12345", readOnly = true)
  private Long id;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "file.pdf", readOnly = true)
  private String fileName;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "application/pdf", readOnly = true)
  private String mimeType;

  @Column(updatable = false)
  @NotNull
  @Schema(type = SchemaType.INTEGER, example = "12345", readOnly = true)
  private Long fileSize;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "abcdefghaicjldjf", readOnly = true)
  private String hash;

  @Column(columnDefinition = "TIMESTAMP", updatable = false)
  @CreationTimestamp
  @NotNull
  @Schema(type = SchemaType.STRING, example = "2021-09-23T09:26:26.464Z", readOnly = true)
  private LocalDateTime created;

  @JsonbTransient
  @Column(updatable = false)
  @Lob
  @Type(type = "org.hibernate.type.TextType")
  private String ocrContent;

  @JsonbTransient
  @JoinColumn(updatable = false)
  @ManyToOne
  @NotNull
  @ToString.Exclude
  private Document document;

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
