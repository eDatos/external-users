package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

import java.sql.Timestamp;

public class OperationEntity {

    public final class Columns {

        private Columns() {
        }

        public static final String ID = "ID";
        public static final String CODE = "CODE";
        public static final String URN = "URN";
        public static final String INDICATOR_SYSTEM = "INDICATOR_SYSTEM";
        public static final String INTERNAL_INVENTORY_DATE = "INTERNAL_INVENTORY_DATE";
        public static final String CURRENTLY_ACTIVE = "CURRENTLY_ACTIVE";
        public static final String RELEASE_CALENDAR = "RELEASE_CALENDAR";
        public static final String RELEASE_CALENDAR_ACCESS = "RELEASE_CALENDAR_ACCESS";
        public static final String INVENTORY_DATE = "INVENTORY_DATE";
        public static final String UPDATE_DATE = "UPDATE_DATE";
        public static final String UUID = "UUID";
        public static final String CREATED_DATE = "CREATED_DATE";
        public static final String CREATED_BY = "CREATED_BY";
        public static final String LAST_UPDATED = "LAST_UPDATED";
        public static final String LAST_UPDATED_BY = "LAST_UPDATED_BY";
        public static final String VERSION = "VERSION";
        public static final String COMMON_METADATA_FK = "COMMON_METADATA_FK";
        public static final String TITLE_FK = "TITLE_FK";
        public static final String ACRONYM_FK = "ACRONYM_FK";
        public static final String SUBJECT_AREA_FK = "SUBJECT_AREA_FK";
        public static final String OBJECTIVE_FK = "OBJECTIVE_FK";
        public static final String DESCRIPTION_FK = "DESCRIPTION_FK";
        public static final String SURVEY_TYPE_FK = "SURVEY_TYPE_FK";
        public static final String OFFICIALITY_TYPE_FK = "OFFICIALITY_TYPE_FK";
        public static final String REL_POL_US_AC_FK = "REL_POL_US_AC_FK";
        public static final String REV_POLICY_FK = "REV_POLICY_FK";
        public static final String REV_PRACTICE_FK = "REV_PRACTICE_FK";
        public static final String SPECIFIC_LEGAL_ACTS_FK = "SPECIFIC_LEGAL_ACTS_FK";
        public static final String SPECIFIC_DATA_SHARING_FK = "SPECIFIC_DATA_SHARING_FK";
        public static final String COMMENT_FK = "COMMENT_FK";
        public static final String NOTES_FK = "NOTES_FK";
        public static final String PROC_STATUS = "PROC_STATUS";
        public static final String STATUS = "STATUS";
    }

    private Long id;
    private String code;
    private String urn;
    private Boolean indicatorSystem;
    private Timestamp internalInventoryDate;
    private Boolean currentlyActive;
    private Boolean releaseCalendar;
    private String releaseCalendarAccess;
    private Timestamp inventoryDate;
    private Timestamp updateDate;
    private String uuid;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private Long version;
    private Long commonMetadataFk;
    private Long titleFk;
    private Long acronymFk;
    private Long subjectAreaFk;
    private Long objectiveFk;
    private Long descriptionFk;
    private Long surveyTypeFk;
    private Long officialityTypeFk;
    private Long relPolUsAcFk;
    private Long revPolicyFk;
    private Long revPracticeFk;
    private Long specificLegalActsFk;
    private Long specificDataSharingFk;
    private Long commentFk;
    private Long notesFk;
    private String procStatus;
    private String status;

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

    public Timestamp getInternalInventoryDate() {
        return internalInventoryDate;
    }

    public void setInternalInventoryDate(Timestamp internalInventoryDate) {
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

    public Long getCommonMetadataFk() {
        return commonMetadataFk;
    }

    public void setCommonMetadataFk(Long commonMetadataFk) {
        this.commonMetadataFk = commonMetadataFk;
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

    public Long getSubjectAreaFk() {
        return subjectAreaFk;
    }

    public void setSubjectAreaFk(Long subjectAreaFk) {
        this.subjectAreaFk = subjectAreaFk;
    }

    public Long getObjectiveFk() {
        return objectiveFk;
    }

    public void setObjectiveFk(Long objectiveFk) {
        this.objectiveFk = objectiveFk;
    }

    public Long getDescriptionFk() {
        return descriptionFk;
    }

    public void setDescriptionFk(Long descriptionFk) {
        this.descriptionFk = descriptionFk;
    }

    public Long getSurveyTypeFk() {
        return surveyTypeFk;
    }

    public void setSurveyTypeFk(Long surveyTypeFk) {
        this.surveyTypeFk = surveyTypeFk;
    }

    public Long getOfficialityTypeFk() {
        return officialityTypeFk;
    }

    public void setOfficialityTypeFk(Long officialityTypeFk) {
        this.officialityTypeFk = officialityTypeFk;
    }

    public Long getRelPolUsAcFk() {
        return relPolUsAcFk;
    }

    public void setRelPolUsAcFk(Long relPolUsAcFk) {
        this.relPolUsAcFk = relPolUsAcFk;
    }

    public Long getRevPolicyFk() {
        return revPolicyFk;
    }

    public void setRevPolicyFk(Long revPolicyFk) {
        this.revPolicyFk = revPolicyFk;
    }

    public Long getRevPracticeFk() {
        return revPracticeFk;
    }

    public void setRevPracticeFk(Long revPracticeFk) {
        this.revPracticeFk = revPracticeFk;
    }

    public Long getSpecificLegalActsFk() {
        return specificLegalActsFk;
    }

    public void setSpecificLegalActsFk(Long specificLegalActsFk) {
        this.specificLegalActsFk = specificLegalActsFk;
    }

    public Long getSpecificDataSharingFk() {
        return specificDataSharingFk;
    }

    public void setSpecificDataSharingFk(Long specificDataSharingFk) {
        this.specificDataSharingFk = specificDataSharingFk;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
