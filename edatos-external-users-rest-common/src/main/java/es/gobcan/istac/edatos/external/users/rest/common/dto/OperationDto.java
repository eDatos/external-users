package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class OperationDto extends AbstractVersionedAndAuditingDto implements Serializable {

    private String code;
    private InternationalStringDto name;
    private CategoryDto category;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InternationalStringDto getName() {
        return name;
    }

    public void setName(InternationalStringDto name) {
        this.name = name;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
