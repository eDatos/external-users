package es.gobcan.istac.edatos.external.users.core.service.validator;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;

@Component
public class FavoriteValidator extends AbstractValidator<FavoriteEntity> {
    public void validate(FavoriteEntity favorite) {
        if (favorite.getOperation() == null && favorite.getCategory() == null) {
            throw new EDatosException(ServiceExceptionType.FAVORITE_NEED_AT_LEAST_OPERATION_OR_CATEGORY);
        }
        if (favorite.getOperation() != null && favorite.getCategory() != null) {
            throw new EDatosException(ServiceExceptionType.FAVORITE_CANNOT_SET_BOTH_OPERATION_AND_CATEGORY);
        }
    }
}
