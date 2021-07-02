package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;

import java.util.List;

public interface NotificationService {

    void createNewExternalUserAccountNotification(ExternalUserEntity externalUserEntity);
    void createDeleteExternalUserAccountNotification(ExternalUserEntity externalUserEntity);
    void createChangePasswordExternaluserAccountNotification(ExternalUserEntity externalUserEntity);
    void createResetPasswordExternaluserAccountNotification(ExternalUserEntity externalUserEntity);

    void createNoticeOfSusbcritionsJob(List<FavoriteEntity> listFavorites);

}
