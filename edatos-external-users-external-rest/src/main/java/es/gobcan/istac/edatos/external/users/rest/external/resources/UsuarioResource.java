package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.edatos.external.users.core.repository.UsuarioRepository;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.core.service.UsuarioService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.UsuarioDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.UsuarioMapper;
import es.gobcan.istac.edatos.external.users.rest.common.resources.AbstractResource;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import es.gobcan.istac.edatos.external.users.rest.common.util.PaginationUtil;
import es.gobcan.istac.edatos.external.users.rest.common.vm.ManagedUserVM;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class UsuarioResource extends AbstractResource {

    private static final String ENTITY_NAME = "userManagement";

    private final UsuarioRepository userRepository;

    private final MailService mailService;

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    private final AuditEventPublisher auditPublisher;

    public UsuarioResource(UsuarioRepository userRepository, MailService mailService, UsuarioService userService, UsuarioMapper userMapper, AuditEventPublisher auditPublisher) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.usuarioService = userService;
        this.usuarioMapper = userMapper;
        this.auditPublisher = auditPublisher;
    }

    @PostMapping("/usuarios")
    @Timed
    @PreAuthorize("@secChecker.puedeCrearUsuario(authentication)")
    public ResponseEntity<UsuarioDto> create(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        if (managedUserVM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_EXISTE, ErrorMessagesConstants.ID_EXISTE)).body(null);
        }

        if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.USUARIO_EXISTE, ErrorMessagesConstants.USUARIO_EXISTE)).body(null);
        }

        UsuarioEntity nuevoUsuario = usuarioMapper.toEntity(managedUserVM);
        nuevoUsuario = usuarioService.create(nuevoUsuario);
        mailService.sendCreationEmail(nuevoUsuario);
        UsuarioDto nuevoUsuarioDto = usuarioMapper.toDto(nuevoUsuario);

        auditPublisher.publish(AuditConstants.USUARIO_CREACION, nuevoUsuarioDto.getLogin());
        return ResponseEntity.created(new URI("/api/usuarios/" + nuevoUsuarioDto.getLogin())).headers(HeaderUtil.createAlert("userManagement.created", nuevoUsuarioDto.getLogin()))
                .body(nuevoUsuarioDto);
    }

    @PutMapping("/usuarios")
    @Timed
    @PreAuthorize("@secChecker.puedeModificarUsuario(authentication, #managedUserVM?.login)")
    public ResponseEntity<UsuarioDto> update(@Valid @RequestBody ManagedUserVM managedUserVM) {
        Optional<UsuarioEntity> usuarioExistente = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());

        if (usuarioExistente.isPresent() && (!usuarioExistente.get().getId().equals(managedUserVM.getId()))) {
            // login already exists, there cannot be two users with the same login
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.USUARIO_EXISTE, ErrorMessagesConstants.USUARIO_EXISTE)).body(null);
        }

        UsuarioEntity usuario = usuarioMapper.toEntity(managedUserVM);
        usuario = usuarioService.update(usuario);
        Optional<UsuarioDto> updatedUser = Optional.ofNullable(usuarioMapper.toDto(usuario));

        auditPublisher.publish(AuditConstants.USUARIO_EDICION, managedUserVM.getLogin());
        return ResponseUtil.wrapOrNotFound(updatedUser, HeaderUtil.createAlert("userManagement.updated", managedUserVM.getLogin()));
    }

    @GetMapping("/usuarios")
    @Timed
    @PreAuthorize("@secChecker.puedeConsultarUsuario(authentication)")
    public ResponseEntity<List<UsuarioDto>> find(Pageable pageable, Boolean includeDeleted, String query) {
        Page<UsuarioDto> page = usuarioService.find(pageable, includeDeleted, query).map(usuarioMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usuarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/usuarios/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @PreAuthorize("@secChecker.puedeConsultarUsuario(authentication)")
    public ResponseEntity<UsuarioDto> get(@PathVariable String login, Boolean includeDeleted) {
        return ResponseUtil.wrapOrNotFound(usuarioService.getUsuarioWithAuthoritiesByLogin(login, includeDeleted).map(usuarioMapper::toDto));
    }

    @DeleteMapping("/usuarios/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @PreAuthorize("@secChecker.puedeBorrarUsuario(authentication)")
    public ResponseEntity<UsuarioDto> delete(@PathVariable String login) {
        UsuarioEntity usuario = usuarioService.delete(login);
        UsuarioDto result = usuarioMapper.toDto(usuario);

        auditPublisher.publish(AuditConstants.USUARIO_DESACTIVACION, login);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, login)).body(result);
    }

    @PutMapping("/usuarios/{login}/restore")
    @Timed
    @PreAuthorize("@secChecker.puedeModificarUsuario(authentication, #login)")
    public ResponseEntity<UsuarioDto> recover(@Valid @PathVariable String login) {
        UsuarioEntity usuario = usuarioService.recover(login);
        UsuarioDto result = usuarioMapper.toDto(usuario);

        auditPublisher.publish(AuditConstants.USUARIO_ACTIVACION, login);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, login)).body(result);
    }

    @GetMapping("/autenticar")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/usuario")
    @Timed
    public ResponseEntity<UsuarioDto> getAccount() {
        UsuarioEntity databaseUser = usuarioService.getUsuarioWithAuthorities();
        return new ResponseEntity<>(databaseUser != null ? usuarioMapper.toDto(databaseUser) : null, HttpStatus.OK);
    }
}
