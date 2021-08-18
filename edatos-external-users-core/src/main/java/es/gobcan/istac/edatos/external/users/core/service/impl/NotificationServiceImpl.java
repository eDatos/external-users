package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.service.DataProtectionPolicyService;
import es.gobcan.istac.edatos.external.users.core.service.NotificationOrganismArgsService;
import io.github.jhipster.config.JHipsterProperties;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.ReceiverBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.service.EDatosApisLocator;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import es.gobcan.istac.edatos.external.users.core.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private static String BASE_URL = "/#/reset-password/change-password?key=";
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MetadataConfigurationService metadataConfigurationService;

    @Autowired
    private EDatosApisLocator eDatosApisLocator;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    @Autowired
    private NotificationOrganismArgsService notificationOrganismArgsService;

    @Autowired
    private DataProtectionPolicyService dataProtectionPolicyService;

    @Override
    public void createNewExternalUserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.register_account.title";
        String messageCode = "notice.message.register_account.text";

        String[] args = notificationOrganismArgsService.argsByOrganism(getOrganism(), externalUserEntity, getLopd());

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail(), args);
    }

    @Override
    public void createDeleteExternalUserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.unsubscribe_account.title";
        String messageCode = "notice.message.unsubscribe_account.text";
        String[] args = notificationOrganismArgsService.argsByOrganism(getOrganism(), externalUserEntity, getLopd());

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail(), args);
    }

    @Override
    public void createChangePasswordExternaluserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.change_password.title";
        String messageCode = "notice.message.change_password.text";

        String[] args = notificationOrganismArgsService.argsByOrganism(getOrganism(), externalUserEntity, getLopd());

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail(), args);
    }

    @Override
    public void createResetPasswordExternaluserAccountNotification(ExternalUserEntity externalUserEntity) {
        String codeSubject = "notice.subject.reset_password.title";
        String messageCode = "notice.message.reset_password.text";
        String baseUrl = jHipsterProperties.getMail().getBaseUrl() + BASE_URL + externalUserEntity.getResetKey();
        ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(notificationOrganismArgsService.argsByOrganism(getOrganism(), externalUserEntity, getLopd())));
        argsList.add(baseUrl);

        createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail(), argsList.toArray(new String[argsList.size()]));
    }

    @Override
    public void createNoticeOfSusbcritionsJob(List<FavoriteEntity> listFavorites) {
        String codeSubject = "notice.subject.subscrition_job.title";
        String messageCode = "notice.message.subscrition_job.text";

        for (FavoriteEntity favorite : listFavorites) {
            ExternalUserEntity externalUserEntity = favorite.getExternalUser();
            ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(notificationOrganismArgsService.argsByOrganism(getOrganism(), externalUserEntity, getLopd())));
            argsList.add(favorite.getExternalOperation().getName().getLocalisedLabel(getLocale()));

            if (externalUserEntity.isEmailNotificationsEnabled() && favorite.getExternalOperation().isNotificationsEnabled()) {
                createNotificationWithReceiver(codeSubject, messageCode, externalUserEntity.getEmail(), argsList.toArray(new String[argsList.size()]));
            }
        }
    }

    private void createNotificationWithReceiver(String codeSubject, String messageCode, String receiver, String[] args) {
        try {
            Notice notice = createNotice(codeSubject, messageCode, receiver, args);

            eDatosApisLocator.getNoticesRestInternalFacadeV10().createNotice(notice);
        } catch (Exception e) {
            log.debug("Error al enviar notificacion : {}", e);
        }
    }

    private Notice createNotice(String codeSubject, String messageCode, String receiver, String[] args) {
        String subject = generateTextI18n(codeSubject, args);
        Message message = createMessage(messageCode, args);

        // @formatter:off
        return NoticeBuilder.notification()
            .withMessages(message)
            .withReceivers(receiver)
            .withSendingApplication(MetamacApplicationsEnum.GESTOR_EXTERNAL_USERS.getName())
            .withSubject(subject)
            .build();
        // @formatter:on
    }

    private Message createMessage(String messageCode, String[] args) {
        String localisedMessage = generateTextI18n(messageCode, args);

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
                .withSendingApplication(MetamacApplicationsEnum.GESTOR_EXTERNAL_USERS.getName()).withSubject(subject).build();

            eDatosApisLocator.getNoticesRestInternalFacadeV10().createNotice(notice);
        } catch (Exception e) {
            log.debug("Error in sending notification : {}", e);
        }
    }

    private String generateTextI18n(String text, String[] args) {
        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(text, args, locale);
    }

    private String getOrganism() {
        return metadataConfigurationService.retrieveOrganisation();
    }

    private String getLopd(){
        return dataProtectionPolicyService.find().getValue().getLocalisedLabel(getLocale());
    }

    private String getLocale(){
        String[] locale = LocaleContextHolder.getLocale().toString().split("_");
        //toLowerCase locale
        return locale[0];
    }
}
