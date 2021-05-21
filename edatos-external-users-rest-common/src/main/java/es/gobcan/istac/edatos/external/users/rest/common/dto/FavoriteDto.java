package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;

public class FavoriteDto extends AbstractVersionedAndAuditingDto implements Serializable {

    private ExternalUserAccountBaseDto externalUser;
    private CategoryDto category;
    private ExternalOperationDto operation;

    public ExternalUserAccountBaseDto getExternalUser() {
        return externalUser;
    }

    public void setExternalUser(ExternalUserAccountBaseDto externalUser) {
        this.externalUser = externalUser;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public ExternalOperationDto getOperation() {
        return operation;
    }

    public void setOperation(ExternalOperationDto operation) {
        this.operation = operation;
    }
}
