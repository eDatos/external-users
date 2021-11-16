package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class CategoryDto extends AbstractVersionedAndAuditingDto {

    private Long subscribers;
    private InternationalStringDto name;
    private Integer index;
    private List<CategoryDto> children = new ArrayList<>();
    private List<ExternalCategoryDto> externalCategories = new ArrayList<>();
    private List<ExternalOperationDto> externalOperations = new ArrayList<>();

    public Long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Long subscribers) {
        this.subscribers = subscribers;
    }

    public InternationalStringDto getName() {
        return name;
    }

    public void setName(InternationalStringDto name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<ExternalCategoryDto> getExternalCategories() {
        return externalCategories;
    }

    public void setExternalCategories(List<ExternalCategoryDto> externalCategories) {
        this.externalCategories = externalCategories;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }

    public List<ExternalOperationDto> getExternalOperations() {
        return externalOperations;
    }

    public void setExternalOperations(List<ExternalOperationDto> externalOperations) {
        this.externalOperations = externalOperations;
    }
}
