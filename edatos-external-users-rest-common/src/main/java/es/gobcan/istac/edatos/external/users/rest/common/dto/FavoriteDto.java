package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.AbstractVersionedAndAuditingDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.interfaces.Identifiable;

public class FavoriteDto extends AbstractVersionedAndAuditingDto implements Serializable, Identifiable {

    private Long id;
    private ExternalUserDto externalUser;
    private OperationDto operation;
    private CategoryDto category;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ExternalUserDto getExternalUser() {
        return externalUser;
    }

    public void setExternalUser(ExternalUserDto externalUser) {
        this.externalUser = externalUser;
    }

    public OperationDto getOperation() {
        return operation;
    }

    public void setOperation(OperationDto operation) {
        this.operation = operation;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
