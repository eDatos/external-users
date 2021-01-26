package es.gobcan.istac.statistical.operations.roadmap.core.service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.UsuarioEntity;

public interface MailService {

    void send(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    void sendFromTemplate(UsuarioEntity user, String templateName, String titleKey);

    void sendCreationEmail(UsuarioEntity user);
}