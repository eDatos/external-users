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

    @ManyToOne
    @JoinColumn(name = "parent_fk")
    private CategoryEntity parent;

    @NotNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<CategoryEntity> children = new HashSet<>();

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
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

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity newParent) {
        if (newParent != null && !newParent.children.contains(this)) {
            newParent.children.add(this);
        } else if (newParent == null && this.parent != null) { // TODO(EDATOS-3357: Review correctness
            this.parent.children.remove(this);
        }
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
}
