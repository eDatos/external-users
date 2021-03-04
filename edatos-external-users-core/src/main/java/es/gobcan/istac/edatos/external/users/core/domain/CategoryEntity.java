package es.gobcan.istac.edatos.external.users.core.domain;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Categories on EDatos are commonly known as 'topics'. They represent a standardized way to group together other
 * semantic related topics and statistical operations.
 * <p>
 * This class uses a subset of attributes from
 * {@code metamac-srm-core:org.siemac.metamac.srm.core.category.domain.CategoryMetamac}.
 */
@Entity
@Table(name = "tb_category")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_category")
    @SequenceGenerator(name = "seq_tb_category", sequenceName = "seq_tb_category", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String code;

    private String nestedCode;

    /**
     * URI to the API of the resource. Useful to guide the user to the API, if needed.
     */
    @Column(name = "uri", nullable = false, length = 4000)
    @Length(max = 4000)
    @NotNull
    private String uri;

    @Column(name = "urn", length = 4000)
    @Length(max = 4000)
    private String urn;

    /**
     * The category name. I.e.: "Forestry and hunting"
     */
    @JoinColumn(name = "name_fk")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InternationalStringEntity name;

    // TODO(EDATOS-3294): Description is not provided by the API nor the internal app. See
    //  I.e. https://estadisticas.arte-consultores.com/structural-resources-internal/#structuralResources/categorySchemes/categoryScheme;id=ISTAC%253ATEMAS_CANARIAS(01.000)
    //  It does seems it's only available for resources (like TEMAS_CANARIAS), same as for
    //  the comments. It does also seems it's for internal consumption, not external.
    //  See this with @frodgar.
    @JoinColumn(name = "description_fk")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InternationalStringEntity description;

    // TODO(EDATOS-3294): Is this needed?
    @JoinColumn(name = "comment_fk")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InternationalStringEntity comment;

    // TODO(EDATOS-3294): Should this created date represent the moment the category was created originally in structural-resources?
    // TODO(EDATOS-3294): Check compatibility with org.joda.time.DateTime
    private ZonedDateTime createdDate;

    // TODO(EDATOS-3294): Check compatibility with org.joda.time.DateTime
    private ZonedDateTime updateDate;

    // TODO(EDATOS-3294): What's the diff between updateDate and lastUpdated?
    // TODO(EDATOS-3294): Check compatibility with org.joda.time.DateTime
    private ZonedDateTime lastUpdated;

    // TODO(EDATOS-3294): Is this needed? Maybe, it seems like an internal identifier not exposed to business. Could be
    //  useful, tho there are a handful of uuid across the Category related attributes (i.e.: maintainable artifact).
    @Column(nullable = false, length = 36, unique = true)
    private String uuid;

    // TODO(EDATOS-3294): Is this optimistic locking? How is the resource version (usually of the type 01.000) saved as Long?
    //  The real versions seems to come from CategoryMetamac.getItemSchemeVersion().getMaintainableArtefact().getVersionLogic();
    //  Not sure at all tho
    @Column(nullable = false)
    private Long version;

    @JoinColumn(name = "parent_fk")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // TODO(EDATOS-3294): Review cascade type
    private CategoryEntity parent;

    @NotNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CategoryEntity> children = new ArrayList<>();
}
