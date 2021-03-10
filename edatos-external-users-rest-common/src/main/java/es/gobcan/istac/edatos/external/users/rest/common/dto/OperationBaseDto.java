package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;

import org.siemac.edatos.core.common.dto.AuditableDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

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
    private ProcStatus procStatus;
    private CategoryDto subjectArea;
    private Status status;

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

    public ProcStatus getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatus procStatus) {
        this.procStatus = procStatus;
    }

    public CategoryDto getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(CategoryDto subjectArea) {
        this.subjectArea = subjectArea;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
