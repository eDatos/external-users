package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.Constants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.rest.common.resources.AbstractResource;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class ExternalUserResource extends AbstractResource {

    private static final String ENTITY_NAME = "userManagement";

    private final ExternalUserRepository externalUserRepository;

  //  private final MailService mailService; // TODO pending confirmation by email

    private final ExternalUserService externalUserService;

    private final ExternalUserMapper externalUserMapper;

    private final AuditEventPublisher auditPublisher;

    public ExternalUserResource(ExternalUserRepository externalUserRepository, /*MailService mailService, */ExternalUserService externalUserService, ExternalUserMapper externalUserMapper, AuditEventPublisher auditPublisher) {
        this.externalUserRepository = externalUserRepository;
      //  this.mailService = mailService;
        this.externalUserService = externalUserService;
        this.externalUserMapper = externalUserMapper;
        this.auditPublisher = auditPublisher;
    }

    @PostMapping("/ext_users")
    @Timed
    @PreAuthorize("@secChecker.puedeCrearUsuario(authentication)")
    public ResponseEntity<ExternalUserDto> create(@Valid @RequestBody ExternalUserDto externalUserDto) throws URISyntaxException {
        if (externalUserDto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_EXISTE, ErrorMessagesConstants.ID_EXISTE)).body(null);
        }

        if (externalUserRepository.findOneByEmail(externalUserDto.getEmail().toLowerCase()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.USUARIO_EXISTE, ErrorMessagesConstants.USUARIO_EXISTE)).body(null);
        }

        ExternalUserEntity newExternalUser = externalUserMapper.toEntity(externalUserDto);
        newExternalUser = externalUserService.create(newExternalUser);
       // mailService.sendCreationEmail(newExternalUser);  // TODO quda pendiente para a versificaión de la creación de usuarios externos
        ExternalUserDto newExternalUserDto = externalUserMapper.toDto(newExternalUser);

        auditPublisher.publish(AuditConstants.USUARIO_CREACION, newExternalUserDto.getEmail());
        return ResponseEntity.created(new URI("/api/ext_users/" + newExternalUserDto.getEmail())).headers(HeaderUtil.createAlert("userManagement.created", newExternalUserDto.getEmail()))
                .body(newExternalUserDto);
    }

  /*  @PutMapping("/ext_users")
    @Timed
    @PreAuthorize("@secChecker.puedeModificarUsuario(authentication, #managedUserVM?.login)")
    public ResponseEntity<ExternalUserDto> update(@Valid @RequestBody ExternalUserDto externalUserDto) {
        Optional<ExternalUserEntity> externalUserExistente = externalUserRepository.findOneByEmail(externalUserDto.getEmail().toLowerCase());

        if (externalUserExistente.isPresent() && (!externalUserExistente.get().getId().equals(externalUserDto.getId()))) {
            // login already exists, there cannot be two users with the same login
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.USUARIO_EXISTE, ErrorMessagesConstants.USUARIO_EXISTE)).body(null);
        }

        ExternalUserEntity externalUser = externalUserMapper.toEntity(externalUserDto);
        externalUser = externalUserService.update(externalUser);
        Optional<ExternalUserDto> updatedUser = Optional.ofNullable(externalUserMapper.toDto(externalUser));

        auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, externalUserDto.getEmail());
        return ResponseUtil.wrapOrNotFound(updatedUser, HeaderUtil.createAlert("userManagement.updated", externalUserDto.getEmail()));
    }

    @DeleteMapping("/ext_users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @PreAuthorize("@secChecker.puedeBorrarUsuario(authentication)")
    public ResponseEntity<ExternalUserDto> delete(@PathVariable String login) {
        ExternalUserEntity usuario = externalUserService.delete(login);
        ExternalUserDto result = externalUserMapper.toDto(usuario);

        auditPublisher.publish(AuditConstants.EXT_USUARIO_DESACTIVACION, login);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, login)).body(result);
    }

    @PutMapping("/ext_users/{email}/restore")
    @Timed
    @PreAuthorize("@secChecker.puedeModificarUsuario(authentication, #email)")
    public ResponseEntity<ExternalUserDto> recover(@Valid @PathVariable String email) {
        ExternalUserEntity usuario = externalUserService.recover(login);
        ExternalUserDto result = externalUserMapper.toDto(usuario);

        auditPublisher.publish(AuditConstants.EXT_USUARIO_ACTIVACION, email);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, email)).body(result);
    }

    @GetMapping("/autenticar")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }
*/ // TODO At the moment we focus on the creation of the user
}
