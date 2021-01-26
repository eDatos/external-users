package es.gobcan.istac.statistical.operations.roadmap.core.service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;

public interface NotificationService {

    void createNotificationForPublishInternallyOperation(OperationEntity operation);

    void createNotificationForPublishExternallyOperation(OperationEntity operation);
}
