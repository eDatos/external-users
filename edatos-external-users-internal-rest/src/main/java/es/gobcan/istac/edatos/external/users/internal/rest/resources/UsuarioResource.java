package es.gobcan.istac.edatos.external.users.internal.rest.resources;

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
import es.gobcan.istac.edatos.external.users.internal.rest.dto.UsuarioDto;
import es.gobcan.istac.edatos.external.users.internal.rest.util.HeaderUtil;
import es.gobcan.istac.edatos.external.users.internal.rest.util.PaginationUtil;
import es.gobcan.istac.edatos.external.users.internal.rest.vm.ManagedUserVM;
import es.gobcan.istac.edatos.external.users.internal.rest.mapper.UsuarioMapper;
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
