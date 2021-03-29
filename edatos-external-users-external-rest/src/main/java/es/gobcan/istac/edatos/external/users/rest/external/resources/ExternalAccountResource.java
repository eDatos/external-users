package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountDto;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountBasicDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserAccountMapper;

@RestController
@RequestMapping("/api")
public class ExternalAccountResource extends AbstractResource {

    public static final String BASE_URL = "/api/external-users";
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

    @GetMapping("/account/me")
    @Timed
    @PreAuthorize("@secCheckerExternal.canUpdateUser(authentication)")
    public ResponseEntity<ExternalUserAccountBasicDto> getAccount() {
        ExternalUserEntity databaseUser = externalUserService.getUsuarioWithAuthorities();
        return new ResponseEntity<>(databaseUser != null ? externalUserMapper.toDto(databaseUser) : null, HttpStatus.OK);
    }

    @PutMapping("/account/update")
    @Timed
    @PreAuthorize("@secCheckerExternal.canUpdateUser(authentication)")
    public ResponseEntity<ExternalUserAccountBasicDto> update(@Valid @RequestBody ExternalUserAccountBasicDto userDto) {
        Optional<ExternalUserEntity> existingUser = externalUserRepository.findOneByEmail(userDto.getEmail().toLowerCase());

        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDto.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.USUARIO_EXISTE, ErrorMessagesConstants.USUARIO_EXISTE)).body(null);
        }

        ExternalUserEntity user = externalUserService.update(externalUserMapper.basicDtoToEntity(userDto));
        Optional<ExternalUserAccountBasicDto> updatedUser = Optional.ofNullable(externalUserMapper.toDto(user));

        auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, userDto.getEmail());
        return ResponseUtil.wrapOrNotFound(updatedUser);
    }

}
