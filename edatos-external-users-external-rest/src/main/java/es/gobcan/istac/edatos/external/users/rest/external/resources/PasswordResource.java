package es.gobcan.istac.edatos.external.users.rest.external.resources;

import javax.validation.Valid;

import es.gobcan.istac.edatos.external.users.core.config.MailConstants;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.KeyAndPasswordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserAccountMapper;

@RestController
@RequestMapping(PasswordResource.BASE_URL)
public class PasswordResource extends AbstractResource {

    public static final String BASE_URL = "/api/recover-password";

    private final ExternalUserRepository externalUserRepository;

    private final MailService mailService;

    private final ExternalUserService externalUserService;

    private final ExternalUserAccountMapper externalUserMapper;

    private final AuditEventPublisher auditPublisher;

    public PasswordResource(ExternalUserRepository externalUserRepository, MailService mailService, ExternalUserService externalUserService, ExternalUserAccountMapper externalUserMapper,
            AuditEventPublisher auditPublisher) {
        this.externalUserRepository = externalUserRepository;
        this.mailService = mailService;
        this.externalUserService = externalUserService;
        this.externalUserMapper = externalUserMapper;
        this.auditPublisher = auditPublisher;
    }

    @PostMapping
    @Timed
    public ResponseEntity<String> recoverPassword(@Valid @RequestBody String email) {

        ExternalUserEntity user = externalUserService.recoverExternalUserAccountPassword(email).get();
        mailService.sendExternalUserEmailTemplate(user, MailConstants.MAIL_RECOVER_PASSWORD_EXT_USER);

        auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, email);
        return ResponseEntity.ok().body("Email was sent");
    }

    @PostMapping("/change-password")
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordDto keyAndPassword) {
        return externalUserService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey()).map(user -> ResponseEntity.ok().body("password was changed"))
                .orElse(ResponseEntity.badRequest().body("password could not be changed"));
    }

    @GetMapping("/key-recovery/{key}")
    @Timed
    public ResponseEntity<Boolean> existsResetKey(@PathVariable String key) {
        return ResponseEntity.ok().body(externalUserService.findOneByResetKey(key).isPresent());
    }

}
