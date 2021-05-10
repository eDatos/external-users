package es.gobcan.istac.edatos.external.users.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

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
@PrimaryKeyJoinColumn(name="external_item_fk")
public class ExternalCategoryEntity extends ExternalItemEntity {
    /**
     * The nested code splits the codes from all the levels by a dot. For example: 060.060_010.060_010_030.
     */
    @Column(unique = true)
    private String nestedCode;

    @ManyToOne
    @JoinColumn(name = "parent_fk")
    private ExternalCategoryEntity parent;

    @NotNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<ExternalCategoryEntity> children = new HashSet<>();

    public String getNestedCode() {
        return nestedCode;
    }

    public void setNestedCode(String nestedCode) {
        this.nestedCode = nestedCode;
    }

    public ExternalCategoryEntity getParent() {
        return parent;
    }

    public void setParent(ExternalCategoryEntity newParent) {
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
}
