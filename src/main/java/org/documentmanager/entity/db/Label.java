package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.Hibernate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Indexed
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
    name = "labelseq",
    sequenceName = "labelseq",
    allocationSize = 1,
    initialValue = 4)
public class Label extends PanacheEntityBase implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "labelseq")
  @Schema(type = SchemaType.INTEGER, example = "3")
  private Long id;

  @Schema(type = SchemaType.STRING, example = "Bank")
  @NotBlank
  private String name;

  @ManyToMany(mappedBy = "labels")
  @JsonbTransient
  @ToString.Exclude
  private List<Document> associatedDocuments;

  @Version @JsonbTransient @NotNull private Integer version;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    final Label label = (Label) o;

    return Objects.equals(id, label.id);
  }

  @Override
  public int hashCode() {
    return 435776578;
  }
}
