package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.Hibernate;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
    name = "metadataseq",
    sequenceName = "metadataseq",
    allocationSize = 1,
    initialValue = 100)
public class Metadata extends PanacheEntityBase implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadataseq")
  @JsonbTransient
  private Long id;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "content-type", readOnly = true)
  private String key;

  @Column(updatable = false)
  @NotBlank
  @Schema(type = SchemaType.STRING, example = "application/pdf", readOnly = true)
  private String value;

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    final Metadata metadata = (Metadata) o;
    return Objects.equals(id, metadata.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, value);
  }
}
