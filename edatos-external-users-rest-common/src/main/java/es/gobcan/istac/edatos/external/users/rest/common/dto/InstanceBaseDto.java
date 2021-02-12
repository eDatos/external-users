package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;

import org.siemac.edatos.core.common.dto.AuditableDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

public class InstanceBaseDto extends AuditableDto implements Identifiable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String urn;
    private Integer order;
    private Instant internalInventoryDate;
    private InternationalStringDto title;
    private InternationalStringDto acronym;
    private InternationalStringDto dataDescription;
    private InstanceTypeDto instanceType;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public InternationalStringDto getDataDescription() {
        return dataDescription;
    }

    public void setDataDescription(InternationalStringDto dataDescription) {
        this.dataDescription = dataDescription;
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
}
