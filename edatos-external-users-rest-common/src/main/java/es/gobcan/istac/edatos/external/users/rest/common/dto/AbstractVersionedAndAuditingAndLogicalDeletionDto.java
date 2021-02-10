package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.time.Instant;

import org.siemac.edatos.core.common.dto.AuditableDto;

public class AbstractVersionedAndAuditingAndLogicalDeletionDto extends AuditableDto {

    private String deletedBy;

    private Instant deletionDate;

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Instant getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Instant deletionDate) {
        this.deletionDate = deletionDate;
    }
}
