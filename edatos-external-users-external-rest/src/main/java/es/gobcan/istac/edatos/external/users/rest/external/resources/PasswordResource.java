package es.gobcan.istac.edatos.external.users.rest.external.resources;

import javax.validation.Valid;

import es.gobcan.istac.edatos.external.users.core.config.MailConstants;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.service.NotificationService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountBaseDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.KeyAndPasswordDto;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserAccountMapper;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(PasswordResource.BASE_URL)
public class PasswordResource extends AbstractResource {

    public static final String BASE_URL = "/api/recover-password";

    private final ExternalUserRepository externalUserRepository;

    private final MailService mailService;
    private final ExternalUserService externalUserService;
    private final NotificationService notificationService;

    private final ExternalUserAccountMapper externalUserMapper;
    private final AuditEventPublisher auditPublisher;
    private final MessageSource messageSource;

    public PasswordResource(ExternalUserRepository externalUserRepository, MailService mailService, ExternalUserService externalUserService, ExternalUserAccountMapper externalUserMapper,
            AuditEventPublisher auditPublisher, MessageSource messageSource, NotificationService notificationService) {
        this.externalUserRepository = externalUserRepository;
        this.mailService = mailService;
        this.externalUserService = externalUserService;
        this.externalUserMapper = externalUserMapper;
        this.auditPublisher = auditPublisher;
        this.messageSource = messageSource;
        this.notificationService = notificationService;
    }

    @PostMapping
    @Timed
    public ResponseEntity<String> recoverPassword(@Valid @RequestBody String email) {
        ExternalUserEntity user = externalUserService.recoverExternalUserAccountPassword(email).get();
        notificationService.createResetPasswordExternaluserAccountNotification(user);

        auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, email);
        return ResponseEntity.ok().body(generateTextI18n("resource.external_users.password.email"));
    }

    @PostMapping("/change-password")
    @Timed
    public ResponseEntity<Void> finishPasswordReset(@RequestBody KeyAndPasswordDto keyAndPassword) {
        try {
            Optional<ExternalUserEntity> user = externalUserService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());
            ExternalUserAccountBaseDto updatedUserDto = externalUserMapper.toBaseDto(user.get());

            auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, updatedUserDto.getEmail());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new EDatosException(ServiceExceptionType.GENERIC_ERROR);
        }
    }

    @GetMapping("/key-recovery/{key}")
    @Timed
    public ResponseEntity<Boolean> existsResetKey(@PathVariable String key) {
        return ResponseEntity.ok().body(externalUserService.findOneByResetKey(key).isPresent());
    }

    private String generateTextI18n(String text) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(text, null, locale);

    }
}
