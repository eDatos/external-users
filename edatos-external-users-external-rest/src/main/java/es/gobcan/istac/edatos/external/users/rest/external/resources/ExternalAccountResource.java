package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountBaseDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserAccountMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

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
        externalUserService.create(newExternalUser);
        mailService.sendCreationEmail(newExternalUser);
        ExternalUserAccountDto newExternalUserDto = externalUserMapper.toDto(newExternalUser);

        auditPublisher.publish(AuditConstants.USUARIO_CREACION, newExternalUserDto.getEmail());
        return ResponseEntity.created(new URI("/api/account/signup/" + newExternalUserDto.getEmail())).headers(HeaderUtil.createAlert("userManagement.created", newExternalUserDto.getEmail()))
                .body(newExternalUserDto);
    }

    @GetMapping("/account")
    @Timed
    @PreAuthorize("@secCheckerExternal.canUpdateUser(authentication)")
    public ResponseEntity<ExternalUserAccountBaseDto> getAccount() {
        ExternalUserEntity databaseUser = externalUserService.getUsuarioWithAuthorities();
        return new ResponseEntity<>(databaseUser != null ? externalUserMapper.toDto(databaseUser) : null, HttpStatus.OK);
    }

    @PutMapping("/account")
    @Timed
    @PreAuthorize("@secCheckerExternal.canUpdateUser(authentication)")
    public ResponseEntity<ExternalUserAccountBaseDto> update(@Valid @RequestBody ExternalUserAccountBaseDto userDto) {
        Optional<ExternalUserEntity> existingUser = externalUserRepository.findOneByEmail(userDto.getEmail().toLowerCase());

        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDto.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.USUARIO_EXISTE, ErrorMessagesConstants.USUARIO_EXISTE)).body(null);
        }

        ExternalUserEntity user = externalUserService.update(externalUserMapper.basicDtoToEntity(userDto));
        Optional<ExternalUserAccountBaseDto> updatedUser = Optional.ofNullable(externalUserMapper.toDto(user));

        auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, userDto.getEmail());
        return ResponseUtil.wrapOrNotFound(updatedUser);
    }

}
