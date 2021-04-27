package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class ExternalOperationDto extends AbstractVersionedAndAuditingDto implements Serializable {

    private String code;
    private String urn;
    private InternationalStringDto name;
    private ExternalCategoryDto category;

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

    public InternationalStringDto getName() {
        return name;
    }

    public void setName(InternationalStringDto name) {
        this.name = name;
    }

    public ExternalCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ExternalCategoryDto category) {
        this.category = category;
    }
}
