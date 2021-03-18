package es.gobcan.istac.edatos.external.users.core.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;

/**
 * A statistical operation is the basic statistical activity planning unit. A statistical
 * operation groups the different realizations of itself (aka 'instances') and can be integrated
 * into a group with other operations (aka, 'families').
 */
@Entity
@Table(name = "tb_operations", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Cache(usage = CacheConcurrencyStrategy.NONE)
@TypeDef(name = "json", typeClass = JsonStringType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class OperationEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_operations")
    @SequenceGenerator(name = "seq_tb_operations", sequenceName = "seq_tb_operations", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false, length = 4000)
    @Length(max = 4000)
    private String urn;

    @NotNull
    @Column(nullable = false)
    private Boolean indicatorSystem;

    private Instant internalInventoryDate;

    @NotNull
    @Column(nullable = false)
    private Boolean currentlyActive;

    private Instant inventoryDate;

    private Instant updateDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "common_metadata_fk")
    private ExternalItemEntity commonMetadata;

    @NotNull
    @Type(type = "json")
    @Column(columnDefinition = "json", nullable = false)
    private InternationalStringVO title;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private InternationalStringVO acronym;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private InternationalStringVO objective;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private InternationalStringVO description;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private InternationalStringVO comment;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private InternationalStringVO notes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcStatus procStatus;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_area_fk")
    private CategoryEntity subjectArea;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_secondary_areas", joinColumns = @JoinColumn(name = "operation_fk"), inverseJoinColumns = @JoinColumn(name = "secondary_subject_area_fk"))
    private final Set<CategoryEntity> secondarySubjectAreas = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_ei_producers", joinColumns = @JoinColumn(name = "operation_fk"), inverseJoinColumns = @JoinColumn(name = "producer_fk"))
    private final Set<ExternalItemEntity> producer = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_ei_reg_responsibles", joinColumns = @JoinColumn(name = "operation_fk"), inverseJoinColumns = @JoinColumn(name = "regional_responsible_fk"))
    private final Set<ExternalItemEntity> regionalResponsible = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_ei_reg_contributors", joinColumns = @JoinColumn(name = "operation_fk"), inverseJoinColumns = @JoinColumn(name = "regional_contributor_fk"))
    private final Set<ExternalItemEntity> regionalContributor = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_ei_publishers", joinColumns = @JoinColumn(name = "operation_fk"), inverseJoinColumns = @JoinColumn(name = "publisher_fk"))
    private final Set<ExternalItemEntity> publisher = new HashSet<>();

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_ei_update_frequency", joinColumns = @JoinColumn(name = "operation_fk"), inverseJoinColumns = @JoinColumn(name = "update_frequency_fk"))
    private final Set<ExternalItemEntity> updateFrequency = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    /**
     * The id is not intended to be changed or assigned manually, but
     * for test purpose it is allowed to assign the id.
     */
    protected void setId(Long id) {
        if ((this.id != null) && !this.id.equals(id)) {
            throw new IllegalArgumentException("Not allowed to change the id property.");
        }
        this.id = id;
    }

    /**
     * Semantic identifier
     */
    public String getCode() {
        return code;
    }

    /**
     * Semantic identifier
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public Boolean getIndicatorSystem() {
        return indicatorSystem;
    }

    public void setIndicatorSystem(Boolean indicatorSystem) {
        this.indicatorSystem = indicatorSystem;
    }

    public Instant getInternalInventoryDate() {
        return internalInventoryDate;
    }

    public void setInternalInventoryDate(Instant internalInventoryDate) {
        this.internalInventoryDate = internalInventoryDate;
    }

    public Boolean getCurrentlyActive() {
        return currentlyActive;
    }

    public void setCurrentlyActive(Boolean currentlyActive) {
        this.currentlyActive = currentlyActive;
    }

    public Instant getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Instant inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public ExternalItemEntity getCommonMetadata() {
        return commonMetadata;
    }

    public void setCommonMetadata(ExternalItemEntity commonMetadata) {
        this.commonMetadata = commonMetadata;
    }

    public InternationalStringVO getTitle() {
        return title;
    }

    public void setTitle(InternationalStringVO title) {
        this.title = title;
    }

    public InternationalStringVO getAcronym() {
        return acronym;
    }

    public void setAcronym(InternationalStringVO acronym) {
        this.acronym = acronym;
    }

    public CategoryEntity getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(CategoryEntity subjectArea) {
        this.subjectArea = subjectArea;
    }

    public InternationalStringVO getObjective() {
        return objective;
    }

    public void setObjective(InternationalStringVO objective) {
        this.objective = objective;
    }

    public InternationalStringVO getDescription() {
        return description;
    }

    public void setDescription(InternationalStringVO description) {
        this.description = description;
    }

    public ProcStatus getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatus procStatus) {
        this.procStatus = procStatus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public InternationalStringVO getComment() {
        return comment;
    }

    public void setComment(InternationalStringVO comment) {
        this.comment = comment;
    }

    public InternationalStringVO getNotes() {
        return notes;
    }

    public void setNotes(InternationalStringVO notes) {
        this.notes = notes;
    }

    public Set<CategoryEntity> getSecondarySubjectAreas() {
        return secondarySubjectAreas;
    }

    public void setSecondarySubjectAreas(Set<CategoryEntity> categories) {
        removeAllSecondarySubjectAreas();
        for (CategoryEntity c : categories) {
            addSecondarySubjectArea(c);
        }
    }

    public void addSecondarySubjectArea(CategoryEntity secondarySubjectAreaElement) {
        secondarySubjectAreas.add(secondarySubjectAreaElement);
    }

    public void removeSecondarySubjectArea(CategoryEntity secondarySubjectAreaElement) {
        secondarySubjectAreas.remove(secondarySubjectAreaElement);
    }

    public void removeAllSecondarySubjectAreas() {
        secondarySubjectAreas.clear();
    }

    /**
     * Metadata PRODUCER. Its type is ORGANIZATION
     */
    public Set<ExternalItemEntity> getProducer() {
        return producer;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getProducer}.
     */
    public void addProducer(ExternalItemEntity producerElement) {
        getProducer().add(producerElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getProducer}.
     */
    public void removeProducer(ExternalItemEntity producerElement) {
        getProducer().remove(producerElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getProducer}.
     */
    public void removeAllProducer() {
        getProducer().clear();
    }

    /**
     * Metadata REGIONAL_RESPONSIBLE. Its type is ORGANIZATION
     */
    public Set<ExternalItemEntity> getRegionalResponsible() {
        return regionalResponsible;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getRegionalResponsible}.
     */
    public void addRegionalResponsible(ExternalItemEntity regionalResponsibleElement) {
        getRegionalResponsible().add(regionalResponsibleElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getRegionalResponsible}.
     */
    public void removeRegionalResponsible(ExternalItemEntity regionalResponsibleElement) {
        getRegionalResponsible().remove(regionalResponsibleElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getRegionalResponsible}.
     */
    public void removeAllRegionalResponsible() {
        getRegionalResponsible().clear();
    }

    /**
     * Metadata REGIONAL_CONTRIBUTOR. Its type is ORGANIZATION
     */
    public Set<ExternalItemEntity> getRegionalContributor() {
        return regionalContributor;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getRegionalContributor}.
     */
    public void addRegionalContributor(ExternalItemEntity regionalContributorElement) {
        getRegionalContributor().add(regionalContributorElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getRegionalContributor}.
     */
    public void removeRegionalContributor(ExternalItemEntity regionalContributorElement) {
        getRegionalContributor().remove(regionalContributorElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getRegionalContributor}.
     */
    public void removeAllRegionalContributor() {
        getRegionalContributor().clear();
    }

    /**
     * Metadata PUBLISHER
     */
    public Set<ExternalItemEntity> getPublisher() {
        return publisher;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getPublisher}.
     */
    public void addPublisher(ExternalItemEntity publisherElement) {
        getPublisher().add(publisherElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getPublisher}.
     */
    public void removePublisher(ExternalItemEntity publisherElement) {
        getPublisher().remove(publisherElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getPublisher}.
     */
    public void removeAllPublisher() {
        getPublisher().clear();
    }

    /**
     * Metadata UPDATE_FREQUENCY. Its type is CODE
     */
    public Set<ExternalItemEntity> getUpdateFrequency() {
        return updateFrequency;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getUpdateFrequency}.
     */
    public void addUpdateFrequency(ExternalItemEntity updateFrequencyElement) {
        getUpdateFrequency().add(updateFrequencyElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getUpdateFrequency}.
     */
    public void removeUpdateFrequency(ExternalItemEntity updateFrequencyElement) {
        getUpdateFrequency().remove(updateFrequencyElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getUpdateFrequency}.
     */
    public void removeAllUpdateFrequency() {
        getUpdateFrequency().clear();
    }

    public static final class Properties {

        private Properties() {
        }

        public static final String CODE = "code";
        public static final String TITLE = "title";
        public static final String PROC_STATUS = "procStatus";
        public static final String URN = "urn";
        public static final String ACRONYM = "acronym";
        public static final String STATUS = "status";
        public static final String DESCRIPTION = "description";
        public static final String CURRENTLY_ACTIVE = "currentlyActive";
        public static final String INDICATOR_SYSTEM = "indicatorSystem";
        public static final String INVENTORY_DATE = "inventoryDate";
        public static final String INTERNAL_INVENTORY_DATE = "internalInventoryDate";
        public static final String SUBJECT_AREA = "subjectArea";
        public static final String SECONDARY_SUBJECT_AREAS = "secondarySubjectAreas";
        public static final String PRODUCER = "producer";
        public static final String PUBLISHER = "publisher";
    }
}
