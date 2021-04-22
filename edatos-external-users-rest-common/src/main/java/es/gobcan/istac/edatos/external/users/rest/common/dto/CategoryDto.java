package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class CategoryDto extends AbstractVersionedAndAuditingDto {

    private String code;
    private String nestedCode;
    private String uri;
    private String urn;
    private InternationalStringDto name;
    private InternationalStringDto description;
    private InternationalStringDto comment;
    private Instant updateDate;
    private Instant lastUpdated;
    private long subscribedUsers;
    private List<CategoryDto> children = new ArrayList<>();

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

    public InternationalStringDto getName() {
        return name;
    }

    public void setName(InternationalStringDto name) {
        this.name = name;
    }

    public InternationalStringDto getDescription() {
        return description;
    }

    public void setDescription(InternationalStringDto description) {
        this.description = description;
    }

    public InternationalStringDto getComment() {
        return comment;
    }

    public void setComment(InternationalStringDto comment) {
        this.comment = comment;
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

    public List<CategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDto> children) {
        this.children = children;
    }

    public long getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(long subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }
}
