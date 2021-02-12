package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;
import java.time.Instant;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

public class FilterDto extends AbstractVersionedAndAuditingDto implements Serializable, Identifiable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String resourceName;
    private String login;
    private String permalink;
    private Instant lastAccessDate;
    private String notes;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
