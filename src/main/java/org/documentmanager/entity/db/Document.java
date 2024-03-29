package org.documentmanager.entity.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.documentmanager.entity.es.EpicBridge;
import org.documentmanager.entity.es.LocalDateTimeBridge;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.ValueBridgeRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Indexed
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SequenceGenerator(
        name = "documentseq",
        sequenceName = "documentseq",
        allocationSize = 1,
        initialValue = 4)
public class Document extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentseq")
    private Long id;
    @FullTextField(analyzer = "german")
    @KeywordField(
            name = "title_sort",
            sortable = Sortable.YES,
            normalizer = "sort"
    )
    @FullTextField(
            name = "title_autocomplete",
            analyzer = "autocomplete_indexing",
            searchAnalyzer = "autocomplete_search"
    )
    @NotBlank
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @FullTextField(analyzer = "german")
    private String description;

    @Column(columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    @CreationTimestamp
    @KeywordField(
            name = "created_sort",
            sortable = Sortable.YES,
            normalizer = "sort",
            valueBridge = @ValueBridgeRef(type = LocalDateTimeBridge.class)
    )
    private LocalDateTime created;

    @Column(columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    @KeywordField(
            name = "lastUpdated_sort",
            sortable = Sortable.YES,
            normalizer = "sort",
            valueBridge = @ValueBridgeRef(type = LocalDateTimeBridge.class))
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "epic_id")
    @FullTextField(analyzer = "german", valueBridge = @ValueBridgeRef(type = EpicBridge.class))
    @KeywordField(
            name = "epic_sort",
            sortable = Sortable.YES,
            normalizer = "sort",
            valueBridge = @ValueBridgeRef(type = EpicBridge.class)
    )
    private Epic epic;

    @ManyToMany
    @JoinTable(
            name = "Document_Label",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    @ToString.Exclude
    @IndexedEmbedded
    @AssociationInverseSide(
            inversePath = @ObjectPath(@PropertyValue(propertyName = "associatedDocuments"))
    )
    private List<Label> labels = new ArrayList<>();

    @OneToMany(
            mappedBy = "document",
            cascade = {CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @IndexedEmbedded
    private List<Asset> assets = new ArrayList<>();

    @OneToMany(mappedBy = "sourceDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<DocumentReference> references = new ArrayList<>();

    @JsonbTransient
    @OneToMany(mappedBy = "targetDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<DocumentReference> referencedBy = new ArrayList<>();

    public static List<Document> lastUpdated(final int amount) {
        return find("order by lastupdated desc")
                .range(0, amount - 1)
                .list();
    }

    public static List<Document> lastCreated(final int amount) {
        return find("order by created desc")
                .range(0, amount - 1)
                .list();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final Document document = (Document) o;

        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return 1422296640;
    }
}
