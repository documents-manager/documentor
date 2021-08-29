package org.documentmanager.entity.db;

import lombok.*;
import org.hibernate.Hibernate;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DocumentReference implements Serializable {
    @EmbeddedId
    @JsonbTransient
    @NotNull
    private DocumentReferenceId id;
    @ManyToOne
    @MapsId("sourceId")
    @JoinColumn(name = "source_id")
    @NotNull
    @JsonbTransient
    private Document sourceDocument;
    @ManyToOne
    @MapsId("targetId")
    @JoinColumn(name = "target_id")
    @NotNull
    private Document targetDocument;
    @Enumerated(EnumType.STRING)
    @NotNull
    private DocumentReferenceType referenceType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocumentReference that = (DocumentReference) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
