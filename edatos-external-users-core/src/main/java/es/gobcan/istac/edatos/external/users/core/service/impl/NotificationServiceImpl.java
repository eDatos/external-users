package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.EDatosApisLocator;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import es.gobcan.istac.edatos.external.users.core.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MetadataConfigurationService metadataConfigurationService;

    @Autowired
    private EDatosApisLocator eDatosApisLocator;

    @Override
    public void createNotificationForPublishInternallyOperation(ExternalOperationEntity operation) {
        // TODO EDATOS-3131
    }

    @Override
    public void createNotificationForPublishExternallyOperation(ExternalOperationEntity operation) {
        // TODO EDATOS-3131
    }

}
