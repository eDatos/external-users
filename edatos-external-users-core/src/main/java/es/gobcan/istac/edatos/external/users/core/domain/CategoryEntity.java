package es.gobcan.istac.edatos.external.users.core.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;

/**
 * This category entity represents our own categories, so the technicians can create
 * them and select other categories and operations extracted from eDatos (and represented
 * here by {@link ExternalCategoryEntity} and {@link ExternalOperationEntity}) to be
 * mapped to one of their own created categories.
 * Categories can be marked as favorites by external users.
 *
 * @see FavoriteEntity
 */
@Entity
@Table(name = "tb_categories")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CategoryEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_categories")
    @SequenceGenerator(name = "seq_tb_categories", sequenceName = "seq_tb_categories", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private InternationalStringVO name;

    /**
     * Determines the position of the node in the tree. Starts at 0, meaning it will be the first,
     * just below it's parent if it has one, or the first of the root.
     */
    @NotNull
    @Column(nullable = false)
    private Integer index = 0;

    @ManyToOne
    @JoinColumn(name = "parent_fk")
    private CategoryEntity parent;

    // DO NOT set orphanRemoval = true in this field, otherwise constraint errors produced by unwanted Hibernate
    // delete sql statements may occur when the tree is updated.
    @NotNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private final Set<CategoryEntity> children = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "tb_categories_external_items", joinColumns = @JoinColumn(name = "category_fk"), inverseJoinColumns = @JoinColumn(name = "external_item_fk"))
    private final Set<ExternalItemEntity> externalItems = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InternationalStringVO getName() {
        return name;
    }

    public void setName(InternationalStringVO name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity newParent) {
        this.parent = newParent;
    }

    /**
     * Do not add/remove elements to this set directly, use the methods designed for it.
     *
     * @see #addChild(CategoryEntity)
     * @see #removeChild(CategoryEntity)
     * @see #clearChildren()
     * @return an unmodifiable set.
     */
    @SuppressWarnings("java:S1452") // wildcard usage
    public Set<? extends CategoryEntity> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public void setChildren(Set<CategoryEntity> categories) {
        clearChildren();
        for (CategoryEntity category : categories) {
            addChild(category);
        }
    }

    public void addChild(CategoryEntity category) {
        category.setParent(this);
        this.children.add(category);
    }

    public void removeChild(CategoryEntity category) {
        category.setParent(null);
        this.children.remove(category);
    }

    public void clearChildren() {
        for (CategoryEntity child : children) {
            child.setParent(null);
        }
        children.clear();
    }

    public Set<ExternalItemEntity> getExternalItems() {
        return externalItems;
    }

    public void setExternalItems(Set<ExternalItemEntity> externalItems) {
        this.externalItems.clear();
        this.externalItems.addAll(externalItems);
    }

    @Override
    public String toString() {
        return "CategoryEntity{" + name + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CategoryEntity))
            return false;
        return id != null && id.equals(((CategoryEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
