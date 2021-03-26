package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Treatment;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingAndLogicalDeletionDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

public class ExternalUserAccountDto extends ExternalUserAccountBasicDto {

    private static final long serialVersionUID = 1L;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
