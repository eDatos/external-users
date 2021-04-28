package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class CategoryDto extends AbstractVersionedAndAuditingDto {

    private InternationalStringDto name;
    private List<CategoryDto> children = new ArrayList<>();

    public InternationalStringDto getName() {
        return name;
    }

    public void setName(InternationalStringDto name) {
        this.name = name;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }
}
