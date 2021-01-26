package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;

public interface NotificationService {

    void createNotificationForPublishInternallyOperation(OperationEntity operation);

    void createNotificationForPublishExternallyOperation(OperationEntity operation);
}
