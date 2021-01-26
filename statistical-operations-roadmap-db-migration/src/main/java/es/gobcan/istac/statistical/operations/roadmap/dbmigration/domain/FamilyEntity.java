package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

import java.sql.Timestamp;

public class FamilyEntity {

    public final class Columns {

        private Columns() {
        }

        public static final String ID = "ID";
        public static final String CODE = "CODE";
        public static final String URN = "URN";
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
        public static final String DESCRIPTION_FK = "DESCRIPTION_FK";
        public static final String PROC_STATUS = "PROC_STATUS";
    }

    private Long id;
    private String code;
    private String urn;
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
    private Long descriptionFk;
    private String procStatus;

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

    public Long getDescriptionFk() {
        return descriptionFk;
    }

    public void setDescriptionFk(Long descriptionFk) {
        this.descriptionFk = descriptionFk;
    }

    public String getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }
}
