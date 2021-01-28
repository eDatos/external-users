package es.gobcan.istac.edatos.external.users.internal.rest.dto;

import java.time.Instant;

import org.siemac.edatos.core.common.dto.AuditableDto;
import org.siemac.edatos.core.common.dto.ExternalItemDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity.StatusEnum;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

public class OperationBaseDto extends AuditableDto implements Identifiable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String urn;
    private Boolean indicatorSystem;
    private Boolean currentlyActive;
    private Instant internalInventoryDate;
    private InternationalStringDto title;
    private InternationalStringDto acronym;
    private InternationalStringDto description;
    private ProcStatusEnum procStatus;
    private ExternalItemDto subjectArea;
    private SurveyTypeDto surveyType;
    private OfficialityTypeDto officialityType;
    private StatusEnum status;

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

    public Boolean getCurrentlyActive() {
        return currentlyActive;
    }

    public void setCurrentlyActive(Boolean currentlyActive) {
        this.currentlyActive = currentlyActive;
    }

    public Instant getInternalInventoryDate() {
        return internalInventoryDate;
    }

    public void setInternalInventoryDate(Instant internalInventoryDate) {
        this.internalInventoryDate = internalInventoryDate;
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

    public InternationalStringDto getDescription() {
        return description;
    }

    public void setDescription(InternationalStringDto description) {
        this.description = description;
    }

    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }

    public ExternalItemDto getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(ExternalItemDto subjectArea) {
        this.subjectArea = subjectArea;
    }

    public SurveyTypeDto getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(SurveyTypeDto surveyType) {
        this.surveyType = surveyType;
    }

    public OfficialityTypeDto getOfficialityType() {
        return officialityType;
    }

    public void setOfficialityType(OfficialityTypeDto officialityType) {
        this.officialityType = officialityType;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
