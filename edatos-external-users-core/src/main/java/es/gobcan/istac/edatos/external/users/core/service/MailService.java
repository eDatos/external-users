package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;

public interface MailService {

    void send(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    void sendFromTemplate(UsuarioEntity user, String templateName, String titleKey);

    void sendCreationEmail(UsuarioEntity user);

    void sendFromTemplate(ExternalUserEntity user, String templateName, String titleKey);

    void sendCreationEmail(ExternalUserEntity user);

}
