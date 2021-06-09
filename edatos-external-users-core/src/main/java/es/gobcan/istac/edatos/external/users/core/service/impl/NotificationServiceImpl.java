package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import org.siemac.edatos.core.common.lang.LocaleUtil;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.ReceiverBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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
    public void createNewExternalUserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.register_account";
        String messageCode = "email.creation.text1";

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail());
    }

    @Override
    public void createDeleteExternalUserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.unsubscribe_account";
        String messageCode = "email.delete_account.confirm";

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail());
    }

    @Override
    public void createChangePasswordExternaluserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.change_password";
        String messageCode = "email.change_password.text";

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail());
    }

    @Override
    public void createResetPasswordExternaluserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.reset_password";
        String messageCode = "email.reset.button";

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail());
    }

    private void createNotificationWithReceiver(String codeSubject, String messageCode, String receiver) {
        try {
            Notice notice = createNotice(codeSubject, messageCode, receiver);

            eDatosApisLocator.getNoticesRestInternalFacadeV10().createNotice(notice);

            eDatosApisLocator.createNoticesRest(notice);
        } catch (Exception e) {
            log.debug("Error al enviar notificacion : {}", e);
        }
    }

    private Notice createNotice(String codeSubject, String messageCode, String receiver) {
        Locale locale = metadataConfigurationService.retrieveLanguageDefaultLocale();
        Locale currentLocale = metadataConfigurationService.retrieveLanguageDefaultLocale();
        String subject = messageSource.getMessage(codeSubject, null, currentLocale);

        Message message = createMessage(locale, messageCode);

        // @formatter:off
        return NoticeBuilder.notification()
            .withMessages(message)
            .withReceivers(receiver)
            .withSendingApplication(MetamacApplicationsEnum.GESTOR_AVISOS.getName())
            .withSubject(subject)
            .withRoles(MetamacRolesEnum.ADMINISTRADOR)
            .build();
        // @formatter:on
    }

    private Message createMessage(Locale locale, String messageCode) {
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);

        // @formatter:off
        return  MessageBuilder.message()
            .withText(localisedMessage)
            .build();
        // @formatter:off
    }

    private Receivers createReceiversList(List<String> emails) {
        Receivers receivers = new Receivers();
        for (String email : emails) {
            receivers.getReceivers().add(ReceiverBuilder.receiver().withUsername(email).build());
        }

        receivers.setTotal(new BigInteger(String.valueOf(receivers.getReceivers().size())));
        return receivers;
    }

    private void createNotificationWithReceivers(String message, String codeSubject, Receivers receivers) {
        try {
            Locale currentLocale = metadataConfigurationService.retrieveLanguageDefaultLocale();
            String subject = messageSource.getMessage(codeSubject, null, currentLocale);

            Notice notice = NoticeBuilder.notification().withMessages(MessageBuilder.message().withText(message).build()).withReceivers(receivers)
                .withSendingApplication(MetamacApplicationsEnum.GESTOR_AVISOS.getName()).withSubject(subject).withRoles(MetamacRolesEnum.ADMINISTRADOR).build();

            eDatosApisLocator.getNoticesRestInternalFacadeV10().createNotice(notice);

            eDatosApisLocator.createNoticesRest(notice);
        } catch (Exception e) {
            log.debug("Error al enviar notificacion : {}", e);
        }
    }

}
