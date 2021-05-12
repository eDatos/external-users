package es.gobcan.istac.edatos.external.users.rest.common.dto;

public class ExternalOperationDto extends ExternalItemDto {
    private ExternalCategoryDto category;

    public ExternalCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ExternalCategoryDto category) {
        this.category = category;
    }
}
