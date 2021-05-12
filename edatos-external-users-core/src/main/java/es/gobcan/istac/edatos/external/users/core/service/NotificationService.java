package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

public interface NotificationService {

    void createNotificationForPublishInternallyOperation(ExternalOperationEntity operation);

    void createNotificationForPublishExternallyOperation(ExternalOperationEntity operation);
}
