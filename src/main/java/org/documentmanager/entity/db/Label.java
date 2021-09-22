package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.hibernate.Hibernate;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
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
  private Long id;

  @NotBlank private String name;

  @ManyToMany(mappedBy = "labels")
  @JsonbTransient
  @ToString.Exclude
  private List<Document> associatedDocuments;

  @Version @JsonbTransient @NotNull private Integer version;

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    final Label label = (Label) o;

    return Objects.equals(id, label.id);
  }

  @Override
  public int hashCode() {
    return 435776578;
  }
}
