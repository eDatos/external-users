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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;

/**
 * Categories are commonly known as 'subjects' or 'areas'. They represent a standardized way to group together other
 * semantic related subjects or statistical operations. In EDatos the categories are handled through the
 * structural resources manager.
 * <p>
 * This class uses a subset of attributes extracted from
 * <p>
 * {@code metamac-srm-core:org.siemac.metamac.srm.core.category.domain.CategoryMetamac}.
 */
@Entity
@Table(name = "tb_categories")
@Cache(usage = CacheConcurrencyStrategy.NONE)
// jsonb is already defined on a package-level, but for some reason liquibase:diff doesn't detect it, so
// it needs to be declared on at least one entity for it to work. Refer to
// https://stackoverflow.com/a/52117748/7611990
// https://stackoverflow.com/a/56030790/7611990
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CategoryEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_categories")
    @SequenceGenerator(name = "seq_tb_categories", sequenceName = "seq_tb_categories", allocationSize = 50, initialValue = 1)
    private Long id;

    /**
     * The code of the category. For example: 060, or 060_010_030.
     */
    @NotNull
    @Column(nullable = false)
    private String code;

    /**
     * The nested code splits the codes from all the levels by a dot. For example: 060.060_010.060_010_030.
     */
    @Column(unique = true)
    private String nestedCode;

    /**
     * The category name. I.e.: "Forestry and hunting"
     */
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

    @NotNull
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<OperationEntity> operations = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNestedCode() {
        return nestedCode;
    }

    public void setNestedCode(String nestedCode) {
        this.nestedCode = nestedCode;
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

    public Set<OperationEntity> getOperations() {
        return operations;
    }

    public void setOperations(Set<OperationEntity> operations) {
        for (OperationEntity operation : operations) {
            operation.setCategory(this);
        }
        this.operations.clear();
        this.operations.addAll(operations);
    }

    public void addOperation(OperationEntity operation) {
        operation.setCategory(this);
        this.operations.add(operation);
    }

    public void removeOperation(OperationEntity operation) {
        operation.setCategory(null);
        this.operations.remove(operation);
    }
}
