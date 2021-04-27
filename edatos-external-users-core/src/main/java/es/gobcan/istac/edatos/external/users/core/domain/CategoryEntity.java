package es.gobcan.istac.edatos.external.users.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<CategoryEntity> children = new HashSet<>();

    // @formatter:off
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_category_external_categories",
               joinColumns = @JoinColumn(name = "category_fk"),
               inverseJoinColumns = @JoinColumn(name = "external_category_fk"))
    private final Set<ExternalCategoryEntity> externalCategories = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_operation_external_operations",
               joinColumns = @JoinColumn(name = "operation_fk"),
               inverseJoinColumns = @JoinColumn(name = "external_operation_fk"))
    private final Set<ExternalOperationEntity> externalOperations = new HashSet<>();
    // @formatter:on

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
        } else if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = newParent;
    }

    public Set<CategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryEntity> categories) {
        for (CategoryEntity category : categories) {
            category.setParent(this);
        }
        this.children.clear();
        this.children.addAll(categories);
    }

    public void addChild(CategoryEntity category) {
        category.setParent(this);
        this.children.add(category);
    }

    public void removeChild(CategoryEntity category) {
        category.setParent(null);
        this.children.remove(category);
    }

    public void addExternalCategory(ExternalCategoryEntity externalCategory) {
        externalCategories.add(externalCategory);
        externalCategory.getCategories().add(this);
    }

    public void removeExternalCategory(ExternalCategoryEntity externalCategory) {
        externalCategories.remove(externalCategory);
        externalCategory.getCategories().remove(this);
    }

    public void setExternalCategory(Set<ExternalCategoryEntity> externalCategories) {
        this.externalCategories.clear();
        for (ExternalCategoryEntity category : externalCategories) {
            addExternalCategory(category);
        }
    }

    public Set<ExternalCategoryEntity> getExternalCategories() {
        return externalCategories;
    }

    public void addExternalOperation(ExternalOperationEntity externalOperation) {
        externalOperations.add(externalOperation);
        externalOperation.getCategories().add(this);
    }

    public void removeExternalOperation(ExternalOperationEntity externalOperation) {
        externalOperations.remove(externalOperation);
        externalOperation.getCategories().remove(this);
    }

    public void setExternalOperation(Set<ExternalOperationEntity> externalOperations) {
        this.externalOperations.clear();
        for (ExternalOperationEntity operation : externalOperations) {
            addExternalOperation(operation);
        }
    }

    public Set<ExternalOperationEntity> getExternalOperations() {
        return externalOperations;
    }
}
