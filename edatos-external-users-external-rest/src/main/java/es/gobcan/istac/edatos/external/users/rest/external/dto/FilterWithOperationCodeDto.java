package es.gobcan.istac.edatos.external.users.rest.external.dto;

import java.io.Serializable;
import java.time.Instant;

import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

public class FilterWithOperationCodeDto implements Serializable, Identifiable {

    private Long id;
    private String name;
    private String resourceName;
    private ExternalUserDto externalUser;
    private String permalink;
    private Instant lastAccessDate;
    private String notes;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public ExternalUserDto getExternalUser() {
        return externalUser;
    }

    public void setExternalUser(ExternalUserDto externalUser) {
        this.externalUser = externalUser;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Instant getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Instant lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
