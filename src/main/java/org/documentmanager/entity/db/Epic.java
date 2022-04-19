package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.Hibernate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Indexed
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SequenceGenerator(name = "epicseq", sequenceName = "epicseq", allocationSize = 1, initialValue = 4)
public class Epic extends PanacheEntityBase implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "epicseq")
  @Schema(type = SchemaType.INTEGER, example = "0")
  private Long id;

  @Schema(type = SchemaType.STRING, example = "Finance")
  @NotBlank
  @FullTextField(analyzer = "german")
  @FullTextField(
          name = "name_autocomplete",
          analyzer = "autocomplete_indexing",
          searchAnalyzer = "autocomplete_search"
  )
  private String name;

  @OneToMany(mappedBy = "epic")
  @JsonbTransient
  @ToString.Exclude
  private List<Document> associatedDocuments;

  public static List<Epic> lastUpdated(final int amount) {
    return null;
  }

  @PreRemove
  private void preRemove() {
    associatedDocuments.forEach(child -> child.setEpic(null));
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    final Epic epic = (Epic) o;

    return Objects.equals(id, epic.id);
  }

  @Override
  public int hashCode() {
    return 852206685;
  }
}
