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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.NotBlank;

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
@Table(name = "tb_external_categories")
@Cache(usage = CacheConcurrencyStrategy.NONE)
// jsonb is already defined on a package-level, but for some reason liquibase:diff doesn't detect it, so
// it needs to be declared on at least one entity for it to work. Refer to
// https://stackoverflow.com/a/52117748/7611990
// https://stackoverflow.com/a/56030790/7611990
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ExternalCategoryEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_external_categories")
    @SequenceGenerator(name = "seq_tb_external_categories", sequenceName = "seq_tb_external_categories", allocationSize = 50, initialValue = 1)
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
     * A Uniform Resource Name (URN) is a Uniform Resource Identifier (URI) that is assigned under
     * the "urn" URI scheme and a particular URN namespace, with the intent that the URN will be a
     * persistent, location-independent resource identifier, according to RFC8141.
     * <p>
     * Example: {@code urn:sdmx:org.sdmx.infomodel.categoryscheme.CategoryScheme=ISTAC:SDMXStatSubMatDomainsWD1(01.000)}.
     */
    @NaturalId
    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String urn;

    /**
     * The category name. I.e.: "Forestry and hunting"
     */
    @NotNull
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private InternationalStringVO name;

    @ManyToOne
    @JoinColumn(name = "parent_fk")
    private ExternalCategoryEntity parent;

    @NotNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<ExternalCategoryEntity> children = new HashSet<>();

    @NotNull
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<ExternalOperationEntity> operations = new HashSet<>();

    @ManyToMany(mappedBy = "externalCategories")
    private Set<CategoryEntity> categories = new HashSet<>();

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

    public ExternalCategoryEntity getParent() {
        return parent;
    }

    public void setParent(ExternalCategoryEntity newParent) {
        if (newParent != null && !newParent.children.contains(this)) {
            newParent.children.add(this);
        } else if (newParent == null && this.parent != null) { // TODO(EDATOS-3357: Review correctness
            this.parent.children.remove(this);
        }
        this.parent = newParent;
    }

    public Set<ExternalCategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<ExternalCategoryEntity> categories) {
        for (ExternalCategoryEntity category : categories) {
            category.setParent(this);
        }
        this.children.clear();
        this.children.addAll(categories);
    }

    public void addChild(ExternalCategoryEntity category) {
        category.setParent(this);
        this.children.add(category);
    }

    public void removeChild(ExternalCategoryEntity category) {
        category.setParent(null);
        this.children.remove(category);
    }

    public Set<ExternalOperationEntity> getOperations() {
        return operations;
    }

    public void setOperations(Set<ExternalOperationEntity> operations) {
        for (ExternalOperationEntity operation : operations) {
            operation.setCategory(this);
        }
        this.operations.clear();
        this.operations.addAll(operations);
    }

    public void addOperation(ExternalOperationEntity operation) {
        operation.setCategory(this);
        this.operations.add(operation);
    }

    public void removeOperation(ExternalOperationEntity operation) {
        operation.setCategory(null);
        this.operations.remove(operation);
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }
}
