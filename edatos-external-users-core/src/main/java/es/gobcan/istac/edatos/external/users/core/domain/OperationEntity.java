package es.gobcan.istac.edatos.external.users.core.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

@Entity
@Table(name = "tb_operations", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class OperationEntity extends AbstractVersionedAndAuditingEntity {

    private static final long serialVersionUID = 7203190836494327022L;

    public enum StatusEnum {
        PLANNING, DESIGN, PRODUCTION, OUT_OF_PRINT;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_operations")
    @SequenceGenerator(name = "seq_tb_operations", sequenceName = "seq_tb_operations", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @NotNull
    @Column(name = "urn", nullable = false, length = 4000)
    @Length(max = 4000)
    private String urn;

    @Column(name = "indicator_system", nullable = false)
    @NotNull
    private Boolean indicatorSystem;

    @Column(name = "internal_inventory_date")
    private Instant internalInventoryDate;

    @Column(name = "currently_active", nullable = false)
    @NotNull
    private Boolean currentlyActive;

    @Column(name = "release_calendar", nullable = false)
    @NotNull
    private Boolean releaseCalendar;

    @Column(name = "release_calendar_access", length = 4000)
    @Length(max = 4000)
    private String releaseCalendarAccess;

    @Column(name = "inventory_date")
    private Instant inventoryDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "uuid", nullable = false, length = 36, unique = true)
    private String uuid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMMON_METADATA_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_COMMON_META15"))
    private ExternalItemEntity commonMetadata;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "TITLE_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_TITLE_FK"))
    @NotNull
    private InternationalStringEntity title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACRONYM_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_ACRONYM_FK"))
    private InternationalStringEntity acronym;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_AREA_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_SUBJECT_ARE70"))
    private ExternalItemEntity subjectArea;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OBJECTIVE_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_OBJECTIVE_FK"))
    private InternationalStringEntity objective;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DESCRIPTION_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_DESCRIPTION82"))
    private InternationalStringEntity description;

    @ManyToOne()
    @JoinColumn(name = "SURVEY_TYPE_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_SURVEY_TYPE11"))
    private SurveyTypeEntity surveyType;

    @ManyToOne()
    @JoinColumn(name = "OFFICIALITY_TYPE_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_OFFICIALITY28"))
    private OfficialityTypeEntity officialityType;

    @Column(name = "PROC_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcStatusEnum procStatus;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REL_POL_US_AC_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_REL_POL_US_51"))
    private InternationalStringEntity relPolUsAc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REV_POLICY_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_REV_POLICY_FK"))
    private InternationalStringEntity revPolicy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REV_PRACTICE_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_REV_PRACTIC99"))
    private InternationalStringEntity revPractice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SPECIFIC_LEGAL_ACTS_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_SPECIFIC_LE58"))
    private InternationalStringEntity specificLegalActs;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SPECIFIC_DATA_SHARING_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_SPECIFIC_DA90"))
    private InternationalStringEntity specificDataSharing;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMMENT_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_COMMENT_FK"))
    private InternationalStringEntity comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NOTES_FK", foreignKey = @ForeignKey(name = "FK_TB_OPERATIONS_NOTES_FK"))
    private InternationalStringEntity notes;

    @ManyToMany(mappedBy = "operations")
    @NotNull
    private Set<FamilyEntity> families = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_SECONDARY_AREAS", joinColumns = @JoinColumn(name = "TB_OPERATIONS"), inverseJoinColumns = @JoinColumn(name = "SECUN_SUBJECT_AREAS_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_SECONDARY_AREAS_TB_11"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> secondarySubjectAreas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operation")
    @OrderBy("order desc")
    @NotNull
    private List<InstanceEntity> instances = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_PRODUCERS", joinColumns = @JoinColumn(name = "TB_OPERATIONS"), inverseJoinColumns = @JoinColumn(name = "PRODUCER_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_PRODUCERS_TB_OPERAT43"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> producer = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_REG_RESPONSIBLES", joinColumns = @JoinColumn(name = "TB_OPERATIONS"), inverseJoinColumns = @JoinColumn(name = "REGIONAL_RESPONSIBLE_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_REG_RESPONSIBLES_TB64"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> regionalResponsible = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_REG_CONTRIBUTORS", joinColumns = @JoinColumn(name = "TB_OPERATIONS"), inverseJoinColumns = @JoinColumn(name = "REGIONAL_CONTRIBUTOR_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_REG_CONTRIBUTORS_TB45"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> regionalContributor = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_PUBLISHERS", joinColumns = @JoinColumn(name = "TB_OPERATIONS"), inverseJoinColumns = @JoinColumn(name = "PUBLISHER_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_PUBLISHERS_TB_OPERA01"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> publisher = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_UPDATE_FREQUENCY", joinColumns = @JoinColumn(name = "TB_OPERATIONS"), inverseJoinColumns = @JoinColumn(name = "UPDATE_FREQUENCY_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_UPDATE_FREQUENCY_TB74"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> updateFrequency = new HashSet<>();

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

    /**
     * URN
     */
    public String getUrn() {
        return urn;
    }

    /**
     * URN
     */
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

    public Boolean getReleaseCalendar() {
        return releaseCalendar;
    }

    public void setReleaseCalendar(Boolean releaseCalendar) {
        this.releaseCalendar = releaseCalendar;
    }

    public String getReleaseCalendarAccess() {
        return releaseCalendarAccess;
    }

    public void setReleaseCalendarAccess(String releaseCalendarAccess) {
        this.releaseCalendarAccess = releaseCalendarAccess;
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

    /**
     * This domain object doesn't have a natural key
     * and this random generated identifier is the
     * unique identifier for this domain object.
     */
    public String getUuid() {
        // lazy init of UUID
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
        }
        return uuid;
    }

    public ExternalItemEntity getCommonMetadata() {
        return commonMetadata;
    }

    public void setCommonMetadata(ExternalItemEntity commonMetadata) {
        this.commonMetadata = commonMetadata;
    }

    public InternationalStringEntity getTitle() {
        return title;
    }

    public void setTitle(InternationalStringEntity title) {
        this.title = title;
    }

    public InternationalStringEntity getAcronym() {
        return acronym;
    }

    public void setAcronym(InternationalStringEntity acronym) {
        this.acronym = acronym;
    }

    public ExternalItemEntity getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(ExternalItemEntity subjectArea) {
        this.subjectArea = subjectArea;
    }

    public InternationalStringEntity getObjective() {
        return objective;
    }

    public void setObjective(InternationalStringEntity objective) {
        this.objective = objective;
    }

    public InternationalStringEntity getDescription() {
        return description;
    }

    public void setDescription(InternationalStringEntity description) {
        this.description = description;
    }

    public SurveyTypeEntity getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(SurveyTypeEntity surveyType) {
        this.surveyType = surveyType;
    }

    public OfficialityTypeEntity getOfficialityType() {
        return officialityType;
    }

    public void setOfficialityType(OfficialityTypeEntity officialityType) {
        this.officialityType = officialityType;
    }

    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public InternationalStringEntity getRelPolUsAc() {
        return relPolUsAc;
    }

    public void setRelPolUsAc(InternationalStringEntity relPolUsAc) {
        this.relPolUsAc = relPolUsAc;
    }

    public InternationalStringEntity getRevPolicy() {
        return revPolicy;
    }

    public void setRevPolicy(InternationalStringEntity revPolicy) {
        this.revPolicy = revPolicy;
    }

    public InternationalStringEntity getRevPractice() {
        return revPractice;
    }

    public void setRevPractice(InternationalStringEntity revPractice) {
        this.revPractice = revPractice;
    }

    public InternationalStringEntity getSpecificLegalActs() {
        return specificLegalActs;
    }

    public void setSpecificLegalActs(InternationalStringEntity specificLegalActs) {
        this.specificLegalActs = specificLegalActs;
    }

    public InternationalStringEntity getSpecificDataSharing() {
        return specificDataSharing;
    }

    public void setSpecificDataSharing(InternationalStringEntity specificDataSharing) {
        this.specificDataSharing = specificDataSharing;
    }

    public InternationalStringEntity getComment() {
        return comment;
    }

    public void setComment(InternationalStringEntity comment) {
        this.comment = comment;
    }

    public InternationalStringEntity getNotes() {
        return notes;
    }

    public void setNotes(InternationalStringEntity notes) {
        this.notes = notes;
    }

    public Set<FamilyEntity> getFamilies() {
        return families;
    }

    /**
     * Adds an object to the bidirectional many-to-many
     * association in both ends.
     * It is added the collection {@link #getFamilies}
     * at this side and to the collection
     * {@link FamilyEntity#getOperations}
     * at the opposite side.
     */
    public void addFamily(FamilyEntity familyElement) {
        getFamilies().add(familyElement);
        familyElement.getOperations().add((OperationEntity) this);
    }

    /**
     * Removes an object from the bidirectional many-to-many
     * association in both ends.
     * It is removed from the collection {@link #getFamilies}
     * at this side and from the collection
     * {@link FamilyEntity#getOperations}
     * at the opposite side.
     */
    public void removeFamily(FamilyEntity familyElement) {
        getFamilies().remove(familyElement);
        familyElement.getOperations().remove((OperationEntity) this);
    }

    /**
     * Removes all object from the bidirectional
     * many-to-many association in both ends.
     * All elements are removed from the collection {@link #getFamilies}
     * at this side and from the collection
     * {@link FamilyEntity#getOperations}
     * at the opposite side.
     */
    public void removeAllFamilies() {
        for (FamilyEntity d : getFamilies()) {
            d.getOperations().remove((OperationEntity) this);
        }
        getFamilies().clear();

    }

    /**
     * Metadata SECONDARY_SUBJECT_AREAS. Its type is CATEGORY
     */
    public Set<ExternalItemEntity> getSecondarySubjectAreas() {
        return secondarySubjectAreas;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getSecondarySubjectAreas}.
     */
    public void addSecondarySubjectArea(ExternalItemEntity secondarySubjectAreaElement) {
        getSecondarySubjectAreas().add(secondarySubjectAreaElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getSecondarySubjectAreas}.
     */
    public void removeSecondarySubjectArea(ExternalItemEntity secondarySubjectAreaElement) {
        getSecondarySubjectAreas().remove(secondarySubjectAreaElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getSecondarySubjectAreas}.
     */
    public void removeAllSecondarySubjectAreas() {
        getSecondarySubjectAreas().clear();
    }

    /**
     * Related instances
     */
    public List<InstanceEntity> getInstances() {
        return instances;
    }

    /**
     * Adds an object to the bidirectional many-to-one
     * association in both ends.
     * It is added the collection {@link #getInstances}
     * at this side and the association
     * {@link InstanceEntity#setOperation}
     * at the opposite side is set.
     */
    public void addInstance(InstanceEntity instanceElement) {
        getInstances().add(instanceElement);
        instanceElement.setOperation((OperationEntity) this);
    }

    /**
     * Removes an object from the bidirectional many-to-one
     * association in both ends.
     * It is removed from the collection {@link #getInstances}
     * at this side and the association
     * {@link InstanceEntity#setOperation}
     * at the opposite side is cleared (nulled).
     */
    public void removeInstance(InstanceEntity instanceElement) {
        getInstances().remove(instanceElement);

        instanceElement.setOperation(null);
    }

    /**
     * Removes all object from the bidirectional
     * many-to-one association in both ends.
     * All elements are removed from the collection {@link #getInstances}
     * at this side and the association
     * {@link InstanceEntity#setOperation}
     * at the opposite side is cleared (nulled).
     */
    public void removeAllInstances() {
        for (InstanceEntity d : getInstances()) {
            d.setOperation(null);
        }

        getInstances().clear();
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

    @PrePersist
    protected void prePersist() {
        getUuid();
    }

    /**
     * This method is used by equals and hashCode.
     * 
     * @return {{@link #getUuid}
     */
    public Object getKey() {
        return getUuid();
    }

    public final class Properties {

        private Properties() {
        }

        public static final String CODE = "code";
        public static final String TITLE = "title";
        public static final String PROC_STATUS = "procStatus";
        public static final String FAMILIES = "families";
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
        public static final String SURVEY_TYPE = "surveyType";
        public static final String OFFICIALITY_TYPE = "officialityType";
    }
}
