package es.gobcan.istac.edatos.external.users.core.domain;

import java.time.Instant;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

/**
 * Categories are commonly known as 'topics'. They represent a standardized way to group together other
 * semantic related topics and statistical operations.
 * <p>
 * This class uses a subset of attributes from
 * {@code metamac-srm-core:org.siemac.metamac.srm.core.category.domain.CategoryMetamac}.
 */
@Entity
@Table(name = "tb_category")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CategoryEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_category")
    @SequenceGenerator(name = "seq_tb_category", sequenceName = "seq_tb_category", allocationSize = 50, initialValue = 1)
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
    private String nestedCode;

    /**
     * URI to the API of the resource. Useful to guide the user to the API, if needed.
     */
    @NotNull
    @Column(nullable = false, length = 4000)
    @Length(max = 4000)
    private String uri;

    /**
     * The unique and legigle identifier of the category. I.e.:
     * {@code urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:TEMAS_CANARIAS(01.000).050.050_010.050_010_040}
     */
    @NotNull
    @Column(nullable = false, length = 4000)
    @Length(max = 4000)
    private String urn;

    /**
     * The category name. I.e.: "Forestry and hunting"
     */
    @NotNull
    @JoinColumn(name = "name_fk")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InternationalStringEntity name;

    @JoinColumn(name = "description_fk")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InternationalStringEntity description;

    @JoinColumn(name = "comment_fk")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InternationalStringEntity comment;

    private Instant updateDate;

    @Column(nullable = false, length = 36, unique = true)
    private String uuid;

    @JoinColumn(name = "parent_fk")
    @ManyToOne(cascade = CascadeType.ALL)
    private CategoryEntity parent;

    @NotNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CategoryEntity> children = new HashSet<>();

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public InternationalStringEntity getName() {
        return name;
    }

    public void setName(InternationalStringEntity name) {
        this.name = name;
    }

    public InternationalStringEntity getDescription() {
        return description;
    }

    public void setDescription(InternationalStringEntity description) {
        this.description = description;
    }

    public InternationalStringEntity getComment() {
        return comment;
    }

    public void setComment(InternationalStringEntity comment) {
        this.comment = comment;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        if (parent != null) {
            parent.removeChild(this);
        }
        this.parent = parent;
    }

    public Set<CategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryEntity> categories) {
        for (CategoryEntity category : categories) {
            category.setParent(this);
        }
        this.children = categories;
    }

    public void addChild(CategoryEntity category) {
        category.setParent(this);
        this.children.add(category);
    }

    public void removeChild(CategoryEntity category) {
        category.setParent(null);
        this.children.remove(category);
    }
}
