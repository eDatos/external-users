package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.dto.AuditableDto;

import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

public class CategoryDto extends AuditableDto implements Identifiable {

    private Long id;
    private String code;
    private String nestedCode;
    private String uri;
    private String urn;
    private InternationalStringEntity name;
    private InternationalStringEntity description;
    private InternationalStringEntity comment;
    private Instant createdDate;
    private Instant updateDate;
    private Instant lastUpdated;
    private String uuid;
    private Long version;
    private CategoryDto parent;
    private List<CategoryDto> children = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNestedCode() {
        return nestedCode;
    }

    public void setNestedCode(String nestedCode) {
        this.nestedCode = nestedCode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public InternationalStringEntity getName() {
        return name;
    }

    public void setName(InternationalStringEntity name) {
        this.name = name;
    }

    public InternationalStringEntity getDescription() {
        return description;
    }

    public void setDescription(InternationalStringEntity description) {
        this.description = description;
    }

    public InternationalStringEntity getComment() {
        return comment;
    }

    public void setComment(InternationalStringEntity comment) {
        this.comment = comment;
    }

    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public CategoryDto getParent() {
        return parent;
    }

    public void setParent(CategoryDto parent) {
        this.parent = parent;
    }

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }
}
