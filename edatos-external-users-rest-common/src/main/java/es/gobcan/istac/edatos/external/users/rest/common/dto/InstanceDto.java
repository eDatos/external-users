package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.siemac.edatos.core.common.dto.AuditableDto;
import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

public class InstanceDto extends AuditableDto implements Identifiable {

    private static final long serialVersionUID = 1L;

    private Integer order;
    private String code;
    private String urn;
    private String basePeriod;
    private Instant internalInventoryDate;
    private Instant inventoryDate;
    private InternationalStringDto title;
    private InternationalStringDto acronym;
    private InternationalStringDto dataDescription;
    private InternationalStringDto statisticalPopulation;
    private InternationalStringDto geographicComparability;
    private InternationalStringDto temporalComparability;
    private InternationalStringDto statConcDef;
    private InternationalStringDto classSystem;
    private InstanceTypeDto instanceType;
    private ProcStatusEnum procStatus;
    private InternationalStringDto docMethod;
    private SurveySourceDto surveySource;
    private CollMethodDto collMethod;
    private InternationalStringDto dataValidation;
    private InternationalStringDto dataCompilation;
    private InternationalStringDto adjustment;
    private InternationalStringDto costBurden;
    private InternationalStringDto qualityDoc;
    private InternationalStringDto qualityAssure;
    private InternationalStringDto qualityAssmnt;
    private InternationalStringDto userNeeds;
    private InternationalStringDto userSat;
    private InternationalStringDto completeness;
    private InternationalStringDto timeliness;
    private InternationalStringDto punctuality;
    private InternationalStringDto accuracyOverall;
    private InternationalStringDto samplingErr;
    private InternationalStringDto nonsamplingErr;
    private InternationalStringDto coherXDomain;
    private InternationalStringDto coherInternal;
    private InternationalStringDto comment;
    private InternationalStringDto notes;
    private Set<ExternalItemDto> statisticalUnit = new HashSet<>();
    private Set<ExternalItemDto> geographicGranularity = new HashSet<>();
    private Set<ExternalItemDto> temporalGranularity = new HashSet<>();
    private Set<ExternalItemDto> unitMeasure = new HashSet<>();
    private Set<ExternalItemDto> statConcDefList = new HashSet<>();
    private Set<ExternalItemDto> classSystemList = new HashSet<>();
    private Set<ExternalItemDto> informationSuppliers = new HashSet<>();
    private Set<ExternalItemDto> freqColl = new HashSet<>();
    private Set<CostDto> cost = new HashSet<>();

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getBasePeriod() {
        return basePeriod;
    }

    public void setBasePeriod(String basePeriod) {
        this.basePeriod = basePeriod;
    }

    public Instant getInternalInventoryDate() {
        return internalInventoryDate;
    }

    public void setInternalInventoryDate(Instant internalInventoryDate) {
        this.internalInventoryDate = internalInventoryDate;
    }

    public Instant getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Instant inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public InternationalStringDto getTitle() {
        return title;
    }

    public void setTitle(InternationalStringDto title) {
        this.title = title;
    }

    public InternationalStringDto getAcronym() {
        return acronym;
    }

    public void setAcronym(InternationalStringDto acronym) {
        this.acronym = acronym;
    }

    public InternationalStringDto getDataDescription() {
        return dataDescription;
    }

    public void setDataDescription(InternationalStringDto dataDescription) {
        this.dataDescription = dataDescription;
    }

    public InternationalStringDto getStatisticalPopulation() {
        return statisticalPopulation;
    }

    public void setStatisticalPopulation(InternationalStringDto statisticalPopulation) {
        this.statisticalPopulation = statisticalPopulation;
    }

    public InternationalStringDto getGeographicComparability() {
        return geographicComparability;
    }

    public void setGeographicComparability(InternationalStringDto geographicComparability) {
        this.geographicComparability = geographicComparability;
    }

    public InternationalStringDto getTemporalComparability() {
        return temporalComparability;
    }

    public void setTemporalComparability(InternationalStringDto temporalComparability) {
        this.temporalComparability = temporalComparability;
    }

    public InternationalStringDto getStatConcDef() {
        return statConcDef;
    }

    public void setStatConcDef(InternationalStringDto statConcDef) {
        this.statConcDef = statConcDef;
    }

    public InternationalStringDto getClassSystem() {
        return classSystem;
    }

    public void setClassSystem(InternationalStringDto classSystem) {
        this.classSystem = classSystem;
    }

    public InstanceTypeDto getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(InstanceTypeDto instanceType) {
        this.instanceType = instanceType;
    }

    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }

    public InternationalStringDto getDocMethod() {
        return docMethod;
    }

    public void setDocMethod(InternationalStringDto docMethod) {
        this.docMethod = docMethod;
    }

    public SurveySourceDto getSurveySource() {
        return surveySource;
    }

    public void setSurveySource(SurveySourceDto surveySource) {
        this.surveySource = surveySource;
    }

    public CollMethodDto getCollMethod() {
        return collMethod;
    }

    public void setCollMethod(CollMethodDto collMethod) {
        this.collMethod = collMethod;
    }

    public InternationalStringDto getDataValidation() {
        return dataValidation;
    }

    public void setDataValidation(InternationalStringDto dataValidation) {
        this.dataValidation = dataValidation;
    }

    public InternationalStringDto getDataCompilation() {
        return dataCompilation;
    }

    public void setDataCompilation(InternationalStringDto dataCompilation) {
        this.dataCompilation = dataCompilation;
    }

    public InternationalStringDto getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(InternationalStringDto adjustment) {
        this.adjustment = adjustment;
    }

    public InternationalStringDto getCostBurden() {
        return costBurden;
    }

    public void setCostBurden(InternationalStringDto costBurden) {
        this.costBurden = costBurden;
    }

    public InternationalStringDto getQualityDoc() {
        return qualityDoc;
    }

    public void setQualityDoc(InternationalStringDto qualityDoc) {
        this.qualityDoc = qualityDoc;
    }

    public InternationalStringDto getQualityAssure() {
        return qualityAssure;
    }

    public void setQualityAssure(InternationalStringDto qualityAssure) {
        this.qualityAssure = qualityAssure;
    }

    public InternationalStringDto getQualityAssmnt() {
        return qualityAssmnt;
    }

    public void setQualityAssmnt(InternationalStringDto qualityAssmnt) {
        this.qualityAssmnt = qualityAssmnt;
    }

    public InternationalStringDto getUserNeeds() {
        return userNeeds;
    }

    public void setUserNeeds(InternationalStringDto userNeeds) {
        this.userNeeds = userNeeds;
    }

    public InternationalStringDto getUserSat() {
        return userSat;
    }

    public void setUserSat(InternationalStringDto userSat) {
        this.userSat = userSat;
    }

    public InternationalStringDto getCompleteness() {
        return completeness;
    }

    public void setCompleteness(InternationalStringDto completeness) {
        this.completeness = completeness;
    }

    public InternationalStringDto getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(InternationalStringDto timeliness) {
        this.timeliness = timeliness;
    }

    public InternationalStringDto getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(InternationalStringDto punctuality) {
        this.punctuality = punctuality;
    }

    public InternationalStringDto getAccuracyOverall() {
        return accuracyOverall;
    }

    public void setAccuracyOverall(InternationalStringDto accuracyOverall) {
        this.accuracyOverall = accuracyOverall;
    }

    public InternationalStringDto getSamplingErr() {
        return samplingErr;
    }

    public void setSamplingErr(InternationalStringDto samplingErr) {
        this.samplingErr = samplingErr;
    }

    public InternationalStringDto getNonsamplingErr() {
        return nonsamplingErr;
    }

    public void setNonsamplingErr(InternationalStringDto nonsamplingErr) {
        this.nonsamplingErr = nonsamplingErr;
    }

    public InternationalStringDto getCoherXDomain() {
        return coherXDomain;
    }

    public void setCoherXDomain(InternationalStringDto coherXDomain) {
        this.coherXDomain = coherXDomain;
    }

    public InternationalStringDto getCoherInternal() {
        return coherInternal;
    }

    public void setCoherInternal(InternationalStringDto coherInternal) {
        this.coherInternal = coherInternal;
    }

    public InternationalStringDto getComment() {
        return comment;
    }

    public void setComment(InternationalStringDto comment) {
        this.comment = comment;
    }

    public InternationalStringDto getNotes() {
        return notes;
    }

    public void setNotes(InternationalStringDto notes) {
        this.notes = notes;
    }

    public Set<ExternalItemDto> getStatisticalUnit() {
        return statisticalUnit;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getStatisticalUnit}.
     */
    public void addStatisticalUnit(ExternalItemDto statisticalUnitElement) {
        getStatisticalUnit().add(statisticalUnitElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getStatisticalUnit}.
     */
    public void removeStatisticalUnit(ExternalItemDto statisticalUnitElement) {
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

    public Set<ExternalItemDto> getGeographicGranularity() {
        return geographicGranularity;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getGeographicGranularity}.
     */
    public void addGeographicGranularity(ExternalItemDto geographicGranularityElement) {
        getGeographicGranularity().add(geographicGranularityElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getGeographicGranularity}.
     */
    public void removeGeographicGranularity(ExternalItemDto geographicGranularityElement) {
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

    public Set<ExternalItemDto> getTemporalGranularity() {
        return temporalGranularity;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getTemporalGranularity}.
     */
    public void addTemporalGranularity(ExternalItemDto temporalGranularityElement) {
        getTemporalGranularity().add(temporalGranularityElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getTemporalGranularity}.
     */
    public void removeTemporalGranularity(ExternalItemDto temporalGranularityElement) {
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

    public Set<ExternalItemDto> getUnitMeasure() {
        return unitMeasure;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getUnitMeasure}.
     */
    public void addUnitMeasure(ExternalItemDto unitMeasureElement) {
        getUnitMeasure().add(unitMeasureElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getUnitMeasure}.
     */
    public void removeUnitMeasure(ExternalItemDto unitMeasureElement) {
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

    public Set<ExternalItemDto> getStatConcDefList() {
        return statConcDefList;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getStatConcDefList}.
     */
    public void addStatConcDefList(ExternalItemDto statConcDefListElement) {
        getStatConcDefList().add(statConcDefListElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getStatConcDefList}.
     */
    public void removeStatConcDefList(ExternalItemDto statConcDefListElement) {
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

    public Set<ExternalItemDto> getClassSystemList() {
        return classSystemList;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getClassSystemList}.
     */
    public void addClassSystemList(ExternalItemDto classSystemListElement) {
        getClassSystemList().add(classSystemListElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getClassSystemList}.
     */
    public void removeClassSystemList(ExternalItemDto classSystemListElement) {
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

    public Set<ExternalItemDto> getInformationSuppliers() {
        return informationSuppliers;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getInformationSuppliers}.
     */
    public void addInformationSupplier(ExternalItemDto informationSupplierElement) {
        getInformationSuppliers().add(informationSupplierElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getInformationSuppliers}.
     */
    public void removeInformationSupplier(ExternalItemDto informationSupplierElement) {
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

    public Set<ExternalItemDto> getFreqColl() {
        return freqColl;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getFreqColl}.
     */
    public void addFreqColl(ExternalItemDto freqCollElement) {
        getFreqColl().add(freqCollElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getFreqColl}.
     */
    public void removeFreqColl(ExternalItemDto freqCollElement) {
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

    public Set<CostDto> getCost() {
        return cost;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getCost}.
     */
    public void addCost(CostDto costElement) {
        getCost().add(costElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getCost}.
     */
    public void removeCost(CostDto costElement) {
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
}
