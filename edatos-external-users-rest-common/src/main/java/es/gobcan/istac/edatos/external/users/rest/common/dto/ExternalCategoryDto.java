package es.gobcan.istac.edatos.external.users.rest.common.dto;

public class ExternalCategoryDto extends ExternalItemDto {

    private String nestedCode;

    public String getNestedCode() {
        return nestedCode;
    }

    public void setNestedCode(String nestedCode) {
        this.nestedCode = nestedCode;
    }
}
