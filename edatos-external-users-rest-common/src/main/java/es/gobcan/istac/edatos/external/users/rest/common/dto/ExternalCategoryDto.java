package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.util.ArrayList;
import java.util.List;

public class ExternalCategoryDto extends ExternalItemDto {

    private String nestedCode;
    private List<ExternalCategoryDto> children = new ArrayList<>();

    public String getNestedCode() {
        return nestedCode;
    }

    public void setNestedCode(String nestedCode) {
        this.nestedCode = nestedCode;
    }

    public List<ExternalCategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<ExternalCategoryDto> children) {
        this.children = children;
    }
}
