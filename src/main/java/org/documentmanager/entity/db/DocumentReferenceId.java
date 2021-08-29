package org.documentmanager.entity.db;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DocumentReferenceId implements Serializable {
    @NotNull
    @Column(name = "source_id")
    private Long sourceId;
    @NotNull
    @Column(name = "target_id")
    private Long targetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocumentReferenceId that = (DocumentReferenceId) o;

        if (!Objects.equals(sourceId, that.sourceId)) return false;
        return Objects.equals(targetId, that.targetId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sourceId);
        result = 31 * result + (Objects.hashCode(targetId));
        return result;
    }
}
