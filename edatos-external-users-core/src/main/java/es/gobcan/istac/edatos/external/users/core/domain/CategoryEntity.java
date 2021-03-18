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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

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
@Table(name = "tb_category")
@Cache(usage = CacheConcurrencyStrategy.NONE)
@TypeDef(name = "json", typeClass = JsonStringType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CategoryEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_category")
    @SequenceGenerator(name = "seq_tb_category", sequenceName = "seq_tb_category", allocationSize = 50, initialValue = 1)
    private Long id;

    /**
     * The code of the category. For example: 060, or 060_010_030.
     */
    @NotNull
    @Column(unique = true, nullable = false)
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
     * The unique and legible identifier of the category. I.e.:
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
    @Type(type = "json")
    @Column(columnDefinition = "json", nullable = false)
    private InternationalStringVO name;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private InternationalStringVO description;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private InternationalStringVO comment;

    private Instant updateDate;

    @JoinColumn(name = "parent_fk")
    @ManyToOne(cascade = CascadeType.ALL)
    private CategoryEntity parent;

    @NotNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<CategoryEntity> children = new HashSet<>();

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

    public InternationalStringVO getName() {
        return name;
    }

    public void setName(InternationalStringVO name) {
        this.name = name;
    }

    public InternationalStringVO getDescription() {
        return description;
    }

    public void setDescription(InternationalStringVO description) {
        this.description = description;
    }

    public InternationalStringVO getComment() {
        return comment;
    }

    public void setComment(InternationalStringVO comment) {
        this.comment = comment;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
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
}
