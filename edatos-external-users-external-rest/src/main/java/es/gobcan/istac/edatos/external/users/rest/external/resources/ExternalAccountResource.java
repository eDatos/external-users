package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.rest.external.dto.ExternalUserAccountDto;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.rest.external.mapper.ExternalUserAccountMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.rest.common.resources.AbstractResource;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class ExternalAccountResource extends AbstractResource {

    private static final String ENTITY_NAME = "userManagement";

    private final ExternalUserRepository externalUserRepository;

    private final MailService mailService;

    private final ExternalUserService externalUserService;

    private final ExternalUserAccountMapper externalUserMapper;

    private final AuditEventPublisher auditPublisher;

    public ExternalAccountResource(ExternalUserRepository externalUserRepository, MailService mailService, ExternalUserService externalUserService, ExternalUserAccountMapper externalUserMapper,
            AuditEventPublisher auditPublisher) {
        this.externalUserRepository = externalUserRepository;
        this.mailService = mailService;
        this.externalUserService = externalUserService;
        this.externalUserMapper = externalUserMapper;
        this.auditPublisher = auditPublisher;
    }

    @PostMapping("/account/signup")
    @Timed
    public ResponseEntity<ExternalUserAccountDto> create(@Valid @RequestBody ExternalUserAccountDto externalUserDto) throws URISyntaxException {
        if (externalUserRepository.findOneByEmail(externalUserDto.getEmail().toLowerCase()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.USUARIO_EXISTE, ErrorMessagesConstants.USUARIO_EXISTE)).body(null);
        }

        ExternalUserEntity newExternalUser = externalUserMapper.toEntity(externalUserDto);
        newExternalUser = externalUserService.create(newExternalUser);
        mailService.sendCreationEmail(newExternalUser);
        ExternalUserAccountDto newExternalUserDto = externalUserMapper.toDto(newExternalUser);

        auditPublisher.publish(AuditConstants.USUARIO_CREACION, newExternalUserDto.getEmail());
        return ResponseEntity.created(new URI("/api/account/signup/" + newExternalUserDto.getEmail())).headers(HeaderUtil.createAlert("userManagement.created", newExternalUserDto.getEmail()))
                .body(newExternalUserDto);
    }

}
