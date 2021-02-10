package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import org.siemac.edatos.core.common.dto.AuditableDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

public class NeedBaseDto extends AuditableDto implements Serializable, Identifiable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String urn;
    private InternationalStringDto title;
    private ProcStatusEnum procStatus;

    @Override
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

    public InternationalStringDto getTitle() {
        return title;
    }

    public void setTitle(InternationalStringDto title) {
        this.title = title;
    }

    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }
}
