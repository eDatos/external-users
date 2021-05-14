package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.util.ArrayList;
import java.util.List;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class CategoryDto extends AbstractVersionedAndAuditingDto {

    private Long subscribers;
    private InternationalStringDto name;
    private Integer index;
    private List<ExternalItemDto> resources;
    private List<CategoryDto> children = new ArrayList<>();

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

    public List<ExternalItemDto> getResources() {
        return resources;
    }

    public void setResources(List<ExternalItemDto> resources) {
        this.resources = resources;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }
}
