package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.Locale;

import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
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
    public void createNotificationForPublishInternallyOperation(OperationEntity operation) {
        // TODO EDATOS-3131
    }

    @Override
    public void createNotificationForPublishExternallyOperation(OperationEntity operation) {
        // TODO EDATOS-3131
    }

    // TODO EDATOS-3121 Código para desarrollo. Borrar al finalizar pruebas
    public void sendNotification() {
        try {
            Locale currentLocale = metadataConfigurationService.retrieveLanguageDefaultLocale();
            String subject = messageSource.getMessage("notice.subject", null, currentLocale);

            Notice notice = NoticeBuilder.notification().withMessages(MessageBuilder.message().withText("Mensaje de prueba 1").build())
                    .withSendingApplication(MetamacApplicationsEnum.GESTOR_INDICADORES.getName()).withSubject(subject).withRoles(MetamacRolesEnum.ADMINISTRADOR).build();
            eDatosApisLocator.getNoticesRestInternalFacadeV10().createNotice(notice);

            /*
             * RestTemplate restTemplate = new RestTemplate();
             * String noticesApiUrlBase = metadataConfigurationService.retrieveNoticesInternalApiUrlBase();
             * HttpEntity<Notice> entity = new HttpEntity<Notice>(notice);
             * ResponseEntity<Response> response = restTemplate.exchange("http://estadisticas.arte-consultores.com/notices-internal/apis/notices-internal/notices", HttpMethod.PUT, entity,
             * Response.class);
             */
        } catch (Exception e) {
            log.debug("Error al enviar notificacion : {}", e);
        }
    }
}
