package es.gobcan.istac.edatos.external.users.core.service.impl;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.service.NotificationOrganismArgsService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public final class NotificationOrganismArgsServiceImpl implements NotificationOrganismArgsService {

    private final MessageSource messageSource;

    private static final String IBESTAT = "IBESTAT";
    private static final String ISTAC = "ISTAC";

    private NotificationOrganismArgsServiceImpl(MessageSource messageSource) {

        this.messageSource = messageSource;
    }

    // EDATOS-3379 Future is likely to vary by organism
    @Override
    public String[] argsByOrganism(String organism, ExternalUserEntity externalUserEntity) {
        switch (organism.toUpperCase(Locale.ROOT)) {
            case IBESTAT:
                return ibestatOrganism(externalUserEntity);
            case ISTAC:
                return istacOrganism(externalUserEntity);
            default:
                return ibestatOrganism(externalUserEntity);
        }
    }

    private String[] getBasicArgs(ExternalUserEntity externalUserEntity) {
        String[] args = {externalUserEntity.getName(), externalUserEntity.getSurname1(), externalUserEntity.getEmail()};
        return args;
    }

    private String[] ibestatOrganism(ExternalUserEntity externalUserEntity) {
        String lopdCode = generateTextI18n("notice.message.lopd");
        String logoCode = generateTextI18n("notice.message.logo.ibestat");
        String[] args = {logoCode, externalUserEntity.getName(), externalUserEntity.getSurname1(), externalUserEntity.getEmail(), lopdCode};
        return args;
    }

    private String[] istacOrganism(ExternalUserEntity externalUserEntity) {
        // EDATOS-3379 Future is likely to vary by organism - Change de code text
        String lopdCode = generateTextI18n("notice.message.lopd");
        String logoCode = generateTextI18n("notice.message.logo.ibestat");

        String[] args = {logoCode, externalUserEntity.getName(), externalUserEntity.getSurname1(), externalUserEntity.getEmail(), lopdCode};
        return args;
    }

    private String generateTextI18n(String text) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(text, null, locale);
    }
}
