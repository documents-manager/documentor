package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
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
        initialValue = 4
)
public class Asset extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetseq")
    private Long id;
    @Column(updatable = false)
    @NotBlank
    private String fileName;
    @Column(updatable = false)
    @NotBlank
    private String mimeType;
    @Column(updatable = false)
    @NotNull
    private Long fileSize;
    @Column(updatable = false)
    @NotBlank
    private String hash;
    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    @CreationTimestamp
    @NotNull
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
    private Document document;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Asset asset = (Asset) o;

        return Objects.equals(id, asset.id);
    }

    @Override
    public int hashCode() {
        return 469637925;
    }
}
