package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;

import org.siemac.edatos.core.common.dto.AuditableDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

public class FamilyDto extends AuditableDto implements Identifiable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String urn;
    private Instant internalInventoryDate;
    private Instant inventoryDate;
    private InternationalStringDto title;
    private InternationalStringDto acronym;
    private InternationalStringDto description;
    private ProcStatusEnum procStatus;

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
}
