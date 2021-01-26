package es.gobcan.istac.statistical.operations.roadmap.core.domain;

import java.time.Instant;
import java.util.HashSet;
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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

@Entity
@Table(name = "tb_instances")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class InstanceEntity extends AbstractVersionedAndAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_instances")
    @SequenceGenerator(name = "seq_tb_instances", sequenceName = "seq_tb_instances", allocationSize = 50, initialValue = 1)
    private Long id;

    @Column(name = "order_idx", nullable = false, length = 10)
    @NotNull
    private Integer order;

    @Column(name = "code", nullable = false, length = 255)
    @NotNull
    private String code;

    @Column(name = "urn", nullable = false, length = 4000)
    @Length(max = 4000)
    @NotNull
    private String urn;

    @Column(name = "base_period", length = 255)
    private String basePeriod;

    @Column(name = "internal_inventory_date")
    private Instant internalInventoryDate;

    @Column(name = "inventory_date")
    private Instant inventoryDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "uuid", nullable = false, length = 36, unique = true)
    private String uuid;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "title_fk", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_TITLE_FK"))
    @NotNull
    private InternationalStringEntity title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACRONYM_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_ACRONYM_FK"))
    private InternationalStringEntity acronym;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "OPERATION_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_OPERATION_FK"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private OperationEntity operation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DATA_DESCRIPTION_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_DATA_DESCRIP53"))
    private InternationalStringEntity dataDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STATISTICAL_POPULATION_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_STATISTICAL_99"))
    private InternationalStringEntity statisticalPopulation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "GEOGRAPHIC_COMPARABILITY_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_GEOGRAPHIC_C80"))
    private InternationalStringEntity geographicComparability;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEMPORAL_COMPARABILITY_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_TEMPORAL_COM97"))
    private InternationalStringEntity temporalComparability;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STAT_CONC_DEF_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_STAT_CONC_DE76"))
    private InternationalStringEntity statConcDef;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLASS_SYSTEM_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_CLASS_SYSTEM68"))
    private InternationalStringEntity classSystem;

    @ManyToOne()
    @JoinColumn(name = "INSTANCE_TYPE_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_INSTANCE_TYP70"))
    private InstanceTypeEntity instanceType;

    @Column(name = "PROC_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProcStatusEnum procStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DOC_METHOD_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_DOC_METHOD_FK"))
    private InternationalStringEntity docMethod;

    @ManyToOne()
    @JoinColumn(name = "SURVEY_SOURCE_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_SURVEY_SOURC94"))
    private SurveySourceEntity surveySource;

    @ManyToOne()
    @JoinColumn(name = "COLL_METHOD_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_COLL_METHOD_FK"))
    private CollMethodEntity collMethod;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DATA_VALIDATION_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_DATA_VALIDAT76"))
    private InternationalStringEntity dataValidation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DATA_COMPILATION_FK", foreignKey = @javax.persistence.ForeignKey(name = "FK_TB_INSTANCES_DATA_COMPILA00"))
    private InternationalStringEntity dataCompilation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADJUSTMENT_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_ADJUSTMENT_FK"))
    private InternationalStringEntity adjustment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COST_BURDEN_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_COST_BURDEN_FK"))
    private InternationalStringEntity costBurden;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QUALITY_DOC_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_QUALITY_DOC_FK"))
    private InternationalStringEntity qualityDoc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QUALITY_ASSURE_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_QUALITY_ASSU87"))
    private InternationalStringEntity qualityAssure;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QUALITY_ASSMNT_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_QUALITY_ASSM60"))
    private InternationalStringEntity qualityAssmnt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_NEEDS_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_USER_NEEDS_FK"))
    private InternationalStringEntity userNeeds;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_SAT_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_USER_SAT_FK"))
    private InternationalStringEntity userSat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPLETENESS_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_COMPLETENESS74"))
    private InternationalStringEntity completeness;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TIMELINESS_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_TIMELINESS_FK"))
    private InternationalStringEntity timeliness;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PUNCTUALITY_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_PUNCTUALITY_FK"))
    private InternationalStringEntity punctuality;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCURACY_OVERALL_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_ACCURACY_OVE59"))
    private InternationalStringEntity accuracyOverall;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SAMPLING_ERR_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_SAMPLING_ERR17"))
    private InternationalStringEntity samplingErr;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NONSAMPLING_ERR_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_NONSAMPLING_24"))
    private InternationalStringEntity nonsamplingErr;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COHER_X_DOMAIN_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_COHER_X_DOMA87"))
    private InternationalStringEntity coherXDomain;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COHER_INTERNAL_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_COHER_INTERN31"))
    private InternationalStringEntity coherInternal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMMENT_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_COMMENT_FK"))
    private InternationalStringEntity comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NOTES_FK", foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_NOTES_FK"))
    private InternationalStringEntity notes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_STATISTICAL_UNITS", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "STATISTICAL_UNIT_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_STATISTICAL_UNITS_T73"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> statisticalUnit = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_GEO_GRANULARITIES", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "GEOGRAPHIC_GRANULARITY_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_GEO_GRANULARITIES_T52"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> geographicGranularity = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_TIME_GRANULARITIES", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "TEMPORAL_GRANULARITY_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_TIME_GRANULARITIES_40"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> temporalGranularity = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_UNITS_MEASURE", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "UNIT_MEASURE_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_UNITS_MEASURE_TB_IN48"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> unitMeasure = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_CONC_DEF_LISTS", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "STAT_CONC_DEF_LIST_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_CONC_DEF_LISTS_TB_I87"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> statConcDefList = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_CLASS_SYSTEM_LISTS", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "CLASS_SYSTEM_LIST_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_CLASS_SYSTEM_LISTS_54"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> classSystemList = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_INF_SUPPLIERS", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "INFORMATION_SUPPLIERS_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_INF_SUPPLIERS_TB_IN63"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> informationSuppliers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_EI_FREQ_COLL", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "FREQ_COLL_FK"), foreignKey = @ForeignKey(name = "FK_TB_EI_FREQ_COLL_TB_INSTAN69"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @NotNull
    private Set<ExternalItemEntity> freqColl = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "TB_INSTANCES_COSTS", joinColumns = @JoinColumn(name = "TB_INSTANCES"), inverseJoinColumns = @JoinColumn(name = "COST_FK"), foreignKey = @ForeignKey(name = "FK_TB_INSTANCES_COSTS_TB_INS32"))
    @NotNull
    private Set<CostEntity> cost = new HashSet<>();

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
     * Order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * Order
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * Metadata CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * Metadata CODE
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

    /**
     * Metadata BASE_PERIOD. Its type is CODE
     */
    public String getBasePeriod() {
        return basePeriod;
    }

    /**
     * Metadata BASE_PERIOD. Its type is CODE
     */
    public void setBasePeriod(String basePeriod) {
        this.basePeriod = basePeriod;
    }

    /**
     * Metadata INTERNAL_INVENTORY_DATE
     */
    public Instant getInternalInventoryDate() {
        return internalInventoryDate;
    }

    /**
     * Metadata INTERNAL_INVENTORY_DATE
     */
    public void setInternalInventoryDate(Instant internalInventoryDate) {
        this.internalInventoryDate = internalInventoryDate;
    }

    /**
     * INVENTORY_DATE
     */
    public Instant getInventoryDate() {
        return inventoryDate;
    }

    /**
     * INVENTORY_DATE
     */
    public void setInventoryDate(Instant inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    /**
     * Last update to optimistic locking
     */
    public Instant getUpdateDate() {
        return updateDate;
    }

    /**
     * Last update to optimistic locking
     */
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

    /**
     * Metadata TITLE
     */
    public InternationalStringEntity getTitle() {
        return title;
    }

    /**
     * Metadata TITLE
     */
    public void setTitle(InternationalStringEntity title) {
        this.title = title;
    }

    /**
     * Metadata TITLE_ALTERNATIVE
     */
    public InternationalStringEntity getAcronym() {
        return acronym;
    }

    /**
     * Metadata TITLE_ALTERNATIVE
     */
    public void setAcronym(InternationalStringEntity acronym) {
        this.acronym = acronym;
    }

    /**
     * Related operation
     */
    public OperationEntity getOperation() {
        return operation;
    }

    /**
     * Related operation
     */
    public void setOperation(OperationEntity operation) {
        this.operation = operation;
    }

    /**
     * Metadata DATA_DESCRIPTION
     */
    public InternationalStringEntity getDataDescription() {
        return dataDescription;
    }

    /**
     * Metadata DATA_DESCRIPTION
     */
    public void setDataDescription(InternationalStringEntity dataDescription) {
        this.dataDescription = dataDescription;
    }

    /**
     * Metadata STATISTICAL_POPULATION
     */
    public InternationalStringEntity getStatisticalPopulation() {
        return statisticalPopulation;
    }

    /**
     * Metadata STATISTICAL_POPULATION
     */
    public void setStatisticalPopulation(InternationalStringEntity statisticalPopulation) {
        this.statisticalPopulation = statisticalPopulation;
    }

    /**
     * Metadata GEOGRAPHIC_COMPARABILITY
     */
    public InternationalStringEntity getGeographicComparability() {
        return geographicComparability;
    }

    /**
     * Metadata GEOGRAPHIC_COMPARABILITY
     */
    public void setGeographicComparability(InternationalStringEntity geographicComparability) {
        this.geographicComparability = geographicComparability;
    }

    /**
     * Metadata TEMPORAL_COMPARABILITY
     */
    public InternationalStringEntity getTemporalComparability() {
        return temporalComparability;
    }

    /**
     * Metadata TEMPORAL_COMPARABILITY
     */
    public void setTemporalComparability(InternationalStringEntity temporalComparability) {
        this.temporalComparability = temporalComparability;
    }

    /**
     * Metadata STAT_CONC_DEF
     */
    public InternationalStringEntity getStatConcDef() {
        return statConcDef;
    }

    /**
     * Metadata STAT_CONC_DEF
     */
    public void setStatConcDef(InternationalStringEntity statConcDef) {
        this.statConcDef = statConcDef;
    }

    /**
     * Metadata CLASS_SYSTEM
     */
    public InternationalStringEntity getClassSystem() {
        return classSystem;
    }

    /**
     * Metadata CLASS_SYSTEM
     */
    public void setClassSystem(InternationalStringEntity classSystem) {
        this.classSystem = classSystem;
    }

    /**
     * Metadata INSTANCE_TYPE
     */
    public InstanceTypeEntity getInstanceType() {
        return instanceType;
    }

    /**
     * Metadata INSTANCE_TYPE
     */
    public void setInstanceType(InstanceTypeEntity instanceType) {
        this.instanceType = instanceType;
    }

    /**
     * Metadata PROC_STATUS
     */
    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    /**
     * Metadata PROC_STATUS
     */
    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }

    /**
     * Metadata DOC_METHOD
     */
    public InternationalStringEntity getDocMethod() {
        return docMethod;
    }

    /**
     * Metadata DOC_METHOD
     */
    public void setDocMethod(InternationalStringEntity docMethod) {
        this.docMethod = docMethod;
    }

    /**
     * Metadata SURVEY_SOURCE
     */
    public SurveySourceEntity getSurveySource() {
        return surveySource;
    }

    /**
     * Metadata SURVEY_SOURCE
     */
    public void setSurveySource(SurveySourceEntity surveySource) {
        this.surveySource = surveySource;
    }

    /**
     * Metadata COLL_METHOD
     */
    public CollMethodEntity getCollMethod() {
        return collMethod;
    }

    /**
     * Metadata COLL_METHOD
     */
    public void setCollMethod(CollMethodEntity collMethod) {
        this.collMethod = collMethod;
    }

    /**
     * Metadata DATA_VALIDATION
     */
    public InternationalStringEntity getDataValidation() {
        return dataValidation;
    }

    /**
     * Metadata DATA_VALIDATION
     */
    public void setDataValidation(InternationalStringEntity dataValidation) {
        this.dataValidation = dataValidation;
    }

    /**
     * Metadata DATA_COMPILATION
     */
    public InternationalStringEntity getDataCompilation() {
        return dataCompilation;
    }

    /**
     * Metadata DATA_COMPILATION
     */
    public void setDataCompilation(InternationalStringEntity dataCompilation) {
        this.dataCompilation = dataCompilation;
    }

    /**
     * Metadata ADJUSTMENT
     */
    public InternationalStringEntity getAdjustment() {
        return adjustment;
    }

    /**
     * Metadata ADJUSTMENT
     */
    public void setAdjustment(InternationalStringEntity adjustment) {
        this.adjustment = adjustment;
    }

    /**
     * Metadata COST_BURDEN
     */
    public InternationalStringEntity getCostBurden() {
        return costBurden;
    }

    /**
     * Metadata COST_BURDEN
     */
    public void setCostBurden(InternationalStringEntity costBurden) {
        this.costBurden = costBurden;
    }

    /**
     * Metadata QUALITY_DOC
     */
    public InternationalStringEntity getQualityDoc() {
        return qualityDoc;
    }

    /**
     * Metadata QUALITY_DOC
     */
    public void setQualityDoc(InternationalStringEntity qualityDoc) {
        this.qualityDoc = qualityDoc;
    }

    /**
     * Metadata QUALITY_ASSURE
     */
    public InternationalStringEntity getQualityAssure() {
        return qualityAssure;
    }

    /**
     * Metadata QUALITY_ASSURE
     */
    public void setQualityAssure(InternationalStringEntity qualityAssure) {
        this.qualityAssure = qualityAssure;
    }

    /**
     * Metadata QUALITY_ASSMNT
     */
    public InternationalStringEntity getQualityAssmnt() {
        return qualityAssmnt;
    }

    /**
     * Metadata QUALITY_ASSMNT
     */
    public void setQualityAssmnt(InternationalStringEntity qualityAssmnt) {
        this.qualityAssmnt = qualityAssmnt;
    }

    /**
     * Metadata USER_NEEDS
     */
    public InternationalStringEntity getUserNeeds() {
        return userNeeds;
    }

    /**
     * Metadata USER_NEEDS
     */
    public void setUserNeeds(InternationalStringEntity userNeeds) {
        this.userNeeds = userNeeds;
    }

    /**
     * Metadata USER_SAT
     */
    public InternationalStringEntity getUserSat() {
        return userSat;
    }

    /**
     * Metadata USER_SAT
     */
    public void setUserSat(InternationalStringEntity userSat) {
        this.userSat = userSat;
    }

    /**
     * Metadata COMPLETENESS
     */
    public InternationalStringEntity getCompleteness() {
        return completeness;
    }

    /**
     * Metadata COMPLETENESS
     */
    public void setCompleteness(InternationalStringEntity completeness) {
        this.completeness = completeness;
    }

    /**
     * Metadata TIMELINESS
     */
    public InternationalStringEntity getTimeliness() {
        return timeliness;
    }

    /**
     * Metadata TIMELINESS
     */
    public void setTimeliness(InternationalStringEntity timeliness) {
        this.timeliness = timeliness;
    }

    /**
     * Metadata PUNCTUALITY
     */
    public InternationalStringEntity getPunctuality() {
        return punctuality;
    }

    /**
     * Metadata PUNCTUALITY
     */
    public void setPunctuality(InternationalStringEntity punctuality) {
        this.punctuality = punctuality;
    }

    /**
     * Metadata ACCURACY_OVERALL
     */
    public InternationalStringEntity getAccuracyOverall() {
        return accuracyOverall;
    }

    /**
     * Metadata ACCURACY_OVERALL
     */
    public void setAccuracyOverall(InternationalStringEntity accuracyOverall) {
        this.accuracyOverall = accuracyOverall;
    }

    /**
     * Metadata SAMPLING_ERR
     */
    public InternationalStringEntity getSamplingErr() {
        return samplingErr;
    }

    /**
     * Metadata SAMPLING_ERR
     */
    public void setSamplingErr(InternationalStringEntity samplingErr) {
        this.samplingErr = samplingErr;
    }

    /**
     * Metadata NONSAMPLING_ERR
     */
    public InternationalStringEntity getNonsamplingErr() {
        return nonsamplingErr;
    }

    /**
     * Metadata NONSAMPLING_ERR
     */
    public void setNonsamplingErr(InternationalStringEntity nonsamplingErr) {
        this.nonsamplingErr = nonsamplingErr;
    }

    /**
     * Metadata COHER_X_DOMAIN
     */
    public InternationalStringEntity getCoherXDomain() {
        return coherXDomain;
    }

    /**
     * Metadata COHER_X_DOMAIN
     */
    public void setCoherXDomain(InternationalStringEntity coherXDomain) {
        this.coherXDomain = coherXDomain;
    }

    /**
     * Metadata COHER_INTERNAL
     */
    public InternationalStringEntity getCoherInternal() {
        return coherInternal;
    }

    /**
     * Metadata COHER_INTERNAL
     */
    public void setCoherInternal(InternationalStringEntity coherInternal) {
        this.coherInternal = coherInternal;
    }

    /**
     * Metadata COMMENT
     */
    public InternationalStringEntity getComment() {
        return comment;
    }

    /**
     * Metadata COMMENT
     */
    public void setComment(InternationalStringEntity comment) {
        this.comment = comment;
    }

    /**
     * Metadata NOTES
     */
    public InternationalStringEntity getNotes() {
        return notes;
    }

    /**
     * Metadata NOTES
     */
    public void setNotes(InternationalStringEntity notes) {
        this.notes = notes;
    }

    /**
     * Metadata STATISTICAL_UNIT. Its type is CONCEPT
     */
    public Set<ExternalItemEntity> getStatisticalUnit() {
        return statisticalUnit;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getStatisticalUnit}.
     */
    public void addStatisticalUnit(ExternalItemEntity statisticalUnitElement) {
        getStatisticalUnit().add(statisticalUnitElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getStatisticalUnit}.
     */
    public void removeStatisticalUnit(ExternalItemEntity statisticalUnitElement) {
        getStatisticalUnit().remove(statisticalUnitElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getStatisticalUnit}.
     */
    public void removeAllStatisticalUnit() {
        getStatisticalUnit().clear();
    }

    /**
     * Metadata GEOGRAPHIC_GRANULARITY. Its type is CODE
     */
    public Set<ExternalItemEntity> getGeographicGranularity() {
        return geographicGranularity;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getGeographicGranularity}.
     */
    public void addGeographicGranularity(ExternalItemEntity geographicGranularityElement) {
        getGeographicGranularity().add(geographicGranularityElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getGeographicGranularity}.
     */
    public void removeGeographicGranularity(ExternalItemEntity geographicGranularityElement) {
        getGeographicGranularity().remove(geographicGranularityElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getGeographicGranularity}.
     */
    public void removeAllGeographicGranularity() {
        getGeographicGranularity().clear();
    }

    /**
     * Metadata TEMPORAL_GRANULARITY. Its type is CODE
     */
    public Set<ExternalItemEntity> getTemporalGranularity() {
        return temporalGranularity;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getTemporalGranularity}.
     */
    public void addTemporalGranularity(ExternalItemEntity temporalGranularityElement) {
        getTemporalGranularity().add(temporalGranularityElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getTemporalGranularity}.
     */
    public void removeTemporalGranularity(ExternalItemEntity temporalGranularityElement) {
        getTemporalGranularity().remove(temporalGranularityElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getTemporalGranularity}.
     */
    public void removeAllTemporalGranularity() {
        getTemporalGranularity().clear();
    }

    /**
     * Metadata UNIT_MEASURE. Its type is CODE
     */
    public Set<ExternalItemEntity> getUnitMeasure() {
        return unitMeasure;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getUnitMeasure}.
     */
    public void addUnitMeasure(ExternalItemEntity unitMeasureElement) {
        getUnitMeasure().add(unitMeasureElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getUnitMeasure}.
     */
    public void removeUnitMeasure(ExternalItemEntity unitMeasureElement) {
        getUnitMeasure().remove(unitMeasureElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getUnitMeasure}.
     */
    public void removeAllUnitMeasure() {
        getUnitMeasure().clear();
    }

    /**
     * Metadata STAT_CONC_DEF_LIST. Its type is CONCEPT_SCHEME
     */
    public Set<ExternalItemEntity> getStatConcDefList() {
        return statConcDefList;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getStatConcDefList}.
     */
    public void addStatConcDefList(ExternalItemEntity statConcDefListElement) {
        getStatConcDefList().add(statConcDefListElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getStatConcDefList}.
     */
    public void removeStatConcDefList(ExternalItemEntity statConcDefListElement) {
        getStatConcDefList().remove(statConcDefListElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getStatConcDefList}.
     */
    public void removeAllStatConcDefList() {
        getStatConcDefList().clear();
    }

    /**
     * Metadata CLASS_SYSTEM_LIST. Its type is CONCEPT_SCHEME
     */
    public Set<ExternalItemEntity> getClassSystemList() {
        return classSystemList;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getClassSystemList}.
     */
    public void addClassSystemList(ExternalItemEntity classSystemListElement) {
        getClassSystemList().add(classSystemListElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getClassSystemList}.
     */
    public void removeClassSystemList(ExternalItemEntity classSystemListElement) {
        getClassSystemList().remove(classSystemListElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getClassSystemList}.
     */
    public void removeAllClassSystemList() {
        getClassSystemList().clear();
    }

    /**
     * Metadata INFORMATION_SUPPLIERS. Its type is ORGANIZATION / CONCEPTS
     */
    public Set<ExternalItemEntity> getInformationSuppliers() {
        return informationSuppliers;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getInformationSuppliers}.
     */
    public void addInformationSupplier(ExternalItemEntity informationSupplierElement) {
        getInformationSuppliers().add(informationSupplierElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getInformationSuppliers}.
     */
    public void removeInformationSupplier(ExternalItemEntity informationSupplierElement) {
        getInformationSuppliers().remove(informationSupplierElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getInformationSuppliers}.
     */
    public void removeAllInformationSuppliers() {
        getInformationSuppliers().clear();
    }

    /**
     * Metadata FREQ_COLL. Its type is CODE
     */
    public Set<ExternalItemEntity> getFreqColl() {
        return freqColl;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getFreqColl}.
     */
    public void addFreqColl(ExternalItemEntity freqCollElement) {
        getFreqColl().add(freqCollElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getFreqColl}.
     */
    public void removeFreqColl(ExternalItemEntity freqCollElement) {
        getFreqColl().remove(freqCollElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getFreqColl}.
     */
    public void removeAllFreqColl() {
        getFreqColl().clear();
    }

    /**
     * Metadata COST
     */
    public Set<CostEntity> getCost() {
        return cost;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getCost}.
     */
    public void addCost(CostEntity costElement) {
        getCost().add(costElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getCost}.
     */
    public void removeCost(CostEntity costElement) {
        getCost().remove(costElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getCost}.
     */
    public void removeAllCost() {
        getCost().clear();
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
        public static final String OPERATION = "operation";
        public static final String URN = "urn";
        public static final String ACRONYM = "acronym";
        public static final String DATA_DESCRIPTION = "dataDescription";
        public static final String GEOGRAPHIC_GRANULARITY = "geographicGranularity";
        public static final String TEMPORAL_GRANULARITY = "temporalGranularity";
        public static final String INSTANCE_TYPE = "instanceType";
        public static final String INTERNAL_INVENTORY_DATE = "internalInventoryDate";
        public static final String INVENTORY_DATE = "inventoryDate";
    }
}
