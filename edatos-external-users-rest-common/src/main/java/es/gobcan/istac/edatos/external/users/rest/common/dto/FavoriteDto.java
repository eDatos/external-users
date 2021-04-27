package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class FavoriteDto extends AbstractVersionedAndAuditingDto implements Serializable {

    private ExternalUserAccountBaseDto externalUser;
    private ExternalOperationDto operation;
    private ExternalCategoryDto category;

    public ExternalUserAccountBaseDto getExternalUser() {
        return externalUser;
    }

    public void setExternalUser(ExternalUserAccountBaseDto externalUser) {
        this.externalUser = externalUser;
    }

    public ExternalOperationDto getOperation() {
        return operation;
    }

    public void setOperation(ExternalOperationDto operation) {
        this.operation = operation;
    }

    public ExternalCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ExternalCategoryDto category) {
        this.category = category;
    }
}
