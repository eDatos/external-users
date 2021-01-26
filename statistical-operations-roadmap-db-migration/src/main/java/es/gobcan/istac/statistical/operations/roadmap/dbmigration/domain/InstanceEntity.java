package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

import java.sql.Timestamp;

public class InstanceEntity {

    public final class Columns {

        private Columns() {
        }

        public static final String ID = "ID";
        public static final String ORDER_IDX = "ORDER_IDX";
        public static final String CODE = "CODE";
        public static final String URN = "URN";
        public static final String BASE_PERIOD = "BASE_PERIOD";
        public static final String INTERNAL_INVENTORY_DATE = "INTERNAL_INVENTORY_DATE";
        public static final String INVENTORY_DATE = "INVENTORY_DATE";
        public static final String UPDATE_DATE = "UPDATE_DATE";
        public static final String UUID = "UUID";
        public static final String CREATED_DATE = "CREATED_DATE";
        public static final String CREATED_BY = "CREATED_BY";
        public static final String LAST_UPDATED = "LAST_UPDATED";
        public static final String LAST_UPDATED_BY = "LAST_UPDATED_BY";
        public static final String VERSION = "VERSION";
        public static final String TITLE_FK = "TITLE_FK";
        public static final String ACRONYM_FK = "ACRONYM_FK";
        public static final String OPERATION_FK = "OPERATION_FK";
        public static final String DATA_DESCRIPTION_FK = "DATA_DESCRIPTION_FK";
        public static final String STATISTICAL_POPULATION_FK = "STATISTICAL_POPULATION_FK";
        public static final String GEOGRAPHIC_COMPARABILITY_FK = "GEOGRAPHIC_COMPARABILITY_FK";
        public static final String TEMPORAL_COMPARABILITY_FK = "TEMPORAL_COMPARABILITY_FK";
        public static final String STAT_CONC_DEF_FK = "STAT_CONC_DEF_FK";
        public static final String CLASS_SYSTEM_FK = "CLASS_SYSTEM_FK";
        public static final String INSTANCE_TYPE_FK = "INSTANCE_TYPE_FK";
        public static final String DOC_METHOD_FK = "DOC_METHOD_FK";
        public static final String SURVEY_SOURCE_FK = "SURVEY_SOURCE_FK";
        public static final String COLL_METHOD_FK = "COLL_METHOD_FK";
        public static final String DATA_VALIDATION_FK = "DATA_VALIDATION_FK";
        public static final String DATA_COMPILATION_FK = "DATA_COMPILATION_FK";
        public static final String ADJUSTMENT_FK = "ADJUSTMENT_FK";
        public static final String COST_BURDEN_FK = "COST_BURDEN_FK";
        public static final String QUALITY_DOC_FK = "QUALITY_DOC_FK";
        public static final String QUALITY_ASSURE_FK = "QUALITY_ASSURE_FK";
        public static final String QUALITY_ASSMNT_FK = "QUALITY_ASSMNT_FK";
        public static final String USER_NEEDS_FK = "USER_NEEDS_FK";
        public static final String USER_SAT_FK = "USER_SAT_FK";
        public static final String COMPLETENESS_FK = "COMPLETENESS_FK";
        public static final String TIMELINESS_FK = "TIMELINESS_FK";
        public static final String PUNCTUALITY_FK = "PUNCTUALITY_FK";
        public static final String ACCURACY_OVERALL_FK = "ACCURACY_OVERALL_FK";
        public static final String SAMPLING_ERR_FK = "SAMPLING_ERR_FK";
        public static final String NONSAMPLING_ERR_FK = "NONSAMPLING_ERR_FK";
        public static final String COHER_X_DOMAIN_FK = "COHER_X_DOMAIN_FK";
        public static final String COHER_INTERNAL_FK = "COHER_INTERNAL_FK";
        public static final String COMMENT_FK = "COMMENT_FK";
        public static final String NOTES_FK = "NOTES_FK";
        public static final String PROC_STATUS = "PROC_STATUS";
    }

    private Long id;
    private Integer orderIdx;
    private String code;
    private String urn;
    private String basePeriod;
    private Timestamp internalInventoryDate;
    private Timestamp inventoryDate;
    private Timestamp updateDate;
    private String uuid;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private Long version;
    private Long titleFk;
    private Long acronymFk;
    private Long operationFk;
    private Long dataDescriptionFk;
    private Long statisticalPopulationFk;
    private Long geographicComparabilityFk;
    private Long temporalComparabilityFk;
    private Long statConcDefFk;
    private Long classSystemFk;
    private Long instanceTypeFk;
    private Long docMethodFk;
    private Long surveySourceFk;
    private Long collMethodFk;
    private Long dataValidationFk;
    private Long dataCompilationFk;
    private Long adjustmentFk;
    private Long costBurdenFk;
    private Long qualityDocFk;
    private Long qualityAssureFk;
    private Long qualityAssmntFk;
    private Long userNeedsFk;
    private Long userSatFk;
    private Long completenessFk;
    private Long timelinessFk;
    private Long punctualityFk;
    private Long accuracyOverallFk;
    private Long samplingErrFk;
    private Long nonsamplingErrFk;
    private Long coherXDomainFk;
    private Long coherInternalFk;
    private Long commentFk;
    private Long notesFk;
    private String procStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderIdx() {
        return orderIdx;
    }

    public void setOrderIdx(Integer orderIdx) {
        this.orderIdx = orderIdx;
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

    public Timestamp getInternalInventoryDate() {
        return internalInventoryDate;
    }

    public void setInternalInventoryDate(Timestamp internalInventoryDate) {
        this.internalInventoryDate = internalInventoryDate;
    }

    public Timestamp getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Timestamp inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getTitleFk() {
        return titleFk;
    }

    public void setTitleFk(Long titleFk) {
        this.titleFk = titleFk;
    }

    public Long getAcronymFk() {
        return acronymFk;
    }

    public void setAcronymFk(Long acronymFk) {
        this.acronymFk = acronymFk;
    }

    public Long getOperationFk() {
        return operationFk;
    }

    public void setOperationFk(Long operationFk) {
        this.operationFk = operationFk;
    }

    public Long getDataDescriptionFk() {
        return dataDescriptionFk;
    }

    public void setDataDescriptionFk(Long dataDescriptionFk) {
        this.dataDescriptionFk = dataDescriptionFk;
    }

    public Long getStatisticalPopulationFk() {
        return statisticalPopulationFk;
    }

    public void setStatisticalPopulationFk(Long statisticalPopulationFk) {
        this.statisticalPopulationFk = statisticalPopulationFk;
    }

    public Long getGeographicComparabilityFk() {
        return geographicComparabilityFk;
    }

    public void setGeographicComparabilityFk(Long geographicComparabilityFk) {
        this.geographicComparabilityFk = geographicComparabilityFk;
    }

    public Long getTemporalComparabilityFk() {
        return temporalComparabilityFk;
    }

    public void setTemporalComparabilityFk(Long temporalComparabilityFk) {
        this.temporalComparabilityFk = temporalComparabilityFk;
    }

    public Long getStatConcDefFk() {
        return statConcDefFk;
    }

    public void setStatConcDefFk(Long statConcDefFk) {
        this.statConcDefFk = statConcDefFk;
    }

    public Long getClassSystemFk() {
        return classSystemFk;
    }

    public void setClassSystemFk(Long classSystemFk) {
        this.classSystemFk = classSystemFk;
    }

    public Long getInstanceTypeFk() {
        return instanceTypeFk;
    }

    public void setInstanceTypeFk(Long instanceTypeFk) {
        this.instanceTypeFk = instanceTypeFk;
    }

    public Long getDocMethodFk() {
        return docMethodFk;
    }

    public void setDocMethodFk(Long docMethodFk) {
        this.docMethodFk = docMethodFk;
    }

    public Long getSurveySourceFk() {
        return surveySourceFk;
    }

    public void setSurveySourceFk(Long surveySourceFk) {
        this.surveySourceFk = surveySourceFk;
    }

    public Long getCollMethodFk() {
        return collMethodFk;
    }

    public void setCollMethodFk(Long collMethodFk) {
        this.collMethodFk = collMethodFk;
    }

    public Long getDataValidationFk() {
        return dataValidationFk;
    }

    public void setDataValidationFk(Long dataValidationFk) {
        this.dataValidationFk = dataValidationFk;
    }

    public Long getDataCompilationFk() {
        return dataCompilationFk;
    }

    public void setDataCompilationFk(Long dataCompilationFk) {
        this.dataCompilationFk = dataCompilationFk;
    }

    public Long getAdjustmentFk() {
        return adjustmentFk;
    }

    public void setAdjustmentFk(Long adjustmentFk) {
        this.adjustmentFk = adjustmentFk;
    }

    public Long getCostBurdenFk() {
        return costBurdenFk;
    }

    public void setCostBurdenFk(Long costBurdenFk) {
        this.costBurdenFk = costBurdenFk;
    }

    public Long getQualityDocFk() {
        return qualityDocFk;
    }

    public void setQualityDocFk(Long qualityDocFk) {
        this.qualityDocFk = qualityDocFk;
    }

    public Long getQualityAssureFk() {
        return qualityAssureFk;
    }

    public void setQualityAssureFk(Long qualityAssureFk) {
        this.qualityAssureFk = qualityAssureFk;
    }

    public Long getQualityAssmntFk() {
        return qualityAssmntFk;
    }

    public void setQualityAssmntFk(Long qualityAssmntFk) {
        this.qualityAssmntFk = qualityAssmntFk;
    }

    public Long getUserNeedsFk() {
        return userNeedsFk;
    }

    public void setUserNeedsFk(Long userNeedsFk) {
        this.userNeedsFk = userNeedsFk;
    }

    public Long getUserSatFk() {
        return userSatFk;
    }

    public void setUserSatFk(Long userSatFk) {
        this.userSatFk = userSatFk;
    }

    public Long getCompletenessFk() {
        return completenessFk;
    }

    public void setCompletenessFk(Long completenessFk) {
        this.completenessFk = completenessFk;
    }

    public Long getTimelinessFk() {
        return timelinessFk;
    }

    public void setTimelinessFk(Long timelinessFk) {
        this.timelinessFk = timelinessFk;
    }

    public Long getPunctualityFk() {
        return punctualityFk;
    }

    public void setPunctualityFk(Long punctualityFk) {
        this.punctualityFk = punctualityFk;
    }

    public Long getAccuracyOverallFk() {
        return accuracyOverallFk;
    }

    public void setAccuracyOverallFk(Long accuracyOverallFk) {
        this.accuracyOverallFk = accuracyOverallFk;
    }

    public Long getSamplingErrFk() {
        return samplingErrFk;
    }

    public void setSamplingErrFk(Long samplingErrFk) {
        this.samplingErrFk = samplingErrFk;
    }

    public Long getNonsamplingErrFk() {
        return nonsamplingErrFk;
    }

    public void setNonsamplingErrFk(Long nonsamplingErrFk) {
        this.nonsamplingErrFk = nonsamplingErrFk;
    }

    public Long getCoherXDomainFk() {
        return coherXDomainFk;
    }

    public void setCoherXDomainFk(Long coherXDomainFk) {
        this.coherXDomainFk = coherXDomainFk;
    }

    public Long getCoherInternalFk() {
        return coherInternalFk;
    }

    public void setCoherInternalFk(Long coherInternalFk) {
        this.coherInternalFk = coherInternalFk;
    }

    public Long getCommentFk() {
        return commentFk;
    }

    public void setCommentFk(Long commentFk) {
        this.commentFk = commentFk;
    }

    public Long getNotesFk() {
        return notesFk;
    }

    public void setNotesFk(Long notesFk) {
        this.notesFk = notesFk;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }
}
