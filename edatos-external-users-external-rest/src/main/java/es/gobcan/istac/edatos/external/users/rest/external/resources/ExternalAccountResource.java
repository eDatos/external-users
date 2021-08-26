package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.service.NotificationService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ChangePasswordDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountBaseDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserAccountDto;
import io.github.jhipster.web.util.ResponseUtil;
import org.siemac.edatos.core.common.exception.EDatosException;
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
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserAccountMapper;

@RestController
@RequestMapping("/api")
public class ExternalAccountResource extends AbstractResource {

    private static final String ENTITY_NAME = "userManagement";

    private final ExternalUserRepository externalUserRepository;
    private final ExternalUserService externalUserService;
    private final ExternalUserAccountMapper externalUserMapper;
    private final AuditEventPublisher auditPublisher;

    private final NotificationService notificationService;

    public ExternalAccountResource(ExternalUserRepository externalUserRepository, ExternalUserService externalUserService, ExternalUserAccountMapper externalUserMapper,
            AuditEventPublisher auditPublisher, NotificationService notificationService) {
        this.externalUserRepository = externalUserRepository;
        this.externalUserService = externalUserService;
        this.externalUserMapper = externalUserMapper;
        this.auditPublisher = auditPublisher;
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secCheckerExternal.canAccessUser(authentication)")
    public ResponseEntity<ExternalUserAccountDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(externalUserMapper.toDto(externalUserService.find(id)));
    }

    @PostMapping("/account/signup")
    @Timed
    public ResponseEntity<ExternalUserAccountDto> create(@Valid @RequestBody ExternalUserAccountDto externalUserDto) throws URISyntaxException {
        ExternalUserEntity newExternalUser = externalUserMapper.toEntity(externalUserDto);
        externalUserService.create(newExternalUser);

        notificationService.createNewExternalUserAccountNotification(newExternalUser);
        ExternalUserAccountDto newExternalUserDto = externalUserMapper.toDto(newExternalUser);

        auditPublisher.publish(AuditConstants.USUARIO_CREACION, newExternalUserDto.getEmail());
        return ResponseEntity.created(new URI("/api/account/signup/" + newExternalUserDto.getEmail())).headers(HeaderUtil.createAlert("userManagement.created", newExternalUserDto.getEmail()))
                .body(newExternalUserDto);
    }

    @PostMapping("/account/logout")
    @Timed
    public ResponseEntity<String> logout(HttpServletRequest request) {
        externalUserService.logout(SecurityUtils.resolveToken(request));
        return ResponseEntity.ok().build();
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

        ExternalUserEntity user = externalUserService.update(externalUserMapper.baseDtoToEntity(userDto));
        Optional<ExternalUserAccountBaseDto> updatedUser = Optional.ofNullable(externalUserMapper.toDto(user));

        auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, userDto.getEmail());
        return ResponseUtil.wrapOrNotFound(updatedUser);
    }

    @DeleteMapping("/account/{id}")
    @Timed
    @PreAuthorize("@secCheckerExternal.canDeleteUser(authentication)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            ExternalUserEntity user = externalUserRepository.findOne(id);
            externalUserService.delete(id);
            notificationService.createDeleteExternalUserAccountNotification(user);
            auditPublisher.publish(AuditConstants.EXT_USUARIO_DESACTIVACION, user.getEmail());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new EDatosException(ServiceExceptionType.GENERIC_ERROR);
        }
    }

    @PostMapping("/account/change-password")
    @Timed
    @PreAuthorize("@secCheckerExternal.canUpdateUser(authentication)")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDto passwordDto) {
        ExternalUserEntity user = externalUserService.getUsuarioWithAuthorities();
        externalUserService.updateExternalUserAccountPassword(user, passwordDto.getCurrentPassword(), passwordDto.getNewPassword());

        notificationService.createChangePasswordExternaluserAccountNotification(user);
        ExternalUserAccountBaseDto updatedUserDto = externalUserMapper.toBaseDto(user);

        auditPublisher.publish(AuditConstants.EXT_USUARIO_EDICION, updatedUserDto.getEmail());
        return ResponseEntity.ok().build();
    }
}
