package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@RequiredArgsConstructor
@SequenceGenerator(
        name = "epicseq",
        sequenceName = "epicseq",
        allocationSize = 1,
        initialValue = 4
)
public class Epic extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "epicseq")
    private Long id;
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "epic", cascade = CascadeType.MERGE)
    @JsonbTransient
    @ToString.Exclude
    private List<Document> associatedDocuments;
    @Version
    @JsonbTransient
    @NotNull
    private Integer version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Epic epic = (Epic) o;

        return Objects.equals(id, epic.id);
    }

    @Override
    public int hashCode() {
        return 852206685;
    }
}
