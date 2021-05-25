package es.gobcan.istac.edatos.external.users.rest.common.dto;

public class ExternalOperationDto extends ExternalItemDto {

    private Long subscribers;
    private ExternalCategoryDto category;

    public ExternalCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ExternalCategoryDto category) {
        this.category = category;
    }

    public Long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Long subscribers) {
        this.subscribers = subscribers;
    }
}
