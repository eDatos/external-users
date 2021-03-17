package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Gender;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Role;
import es.gobcan.istac.edatos.external.users.core.repository.UsuarioRepository;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.core.service.UsuarioService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.UsuarioDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.UsuarioMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see AccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class AccountResourceInternalTest {

    private static final String ROL_ADMIN = "ADMINISTRADOR";

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @SuppressWarnings("rawtypes")
    @Autowired
    private HttpMessageConverter[] httpMessageConverters;

    @Mock
    private UsuarioService mockUserService;

    @Mock
    private MailService mockMailService;

    @Autowired
    private AuditEventPublisher auditPublisher;

    private MockMvc restUserMockMvc;

    private MockMvc restMvc;

    private HashSet<Role> mockRolSet(Role role) {
        return new HashSet<>(Collections.singletonList(role));
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        UsuarioResource accountResource = new UsuarioResource(userRepository, null, userService, usuarioMapper, auditPublisher);

        UsuarioResource accountUserMockResource = new UsuarioResource(userRepository, null, mockUserService, usuarioMapper, auditPublisher);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).setMessageConverters(httpMessageConverters).build();
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource).build();
    }

    @Test
    public void testNonautenticardUser() throws Exception {
        restUserMockMvc.perform(get("/api/autenticar").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(""));
    }

    @Test
    public void testautenticardUser() throws Exception {
        restUserMockMvc.perform(get("/api/autenticar").with(request -> {
            request.setRemoteUser("test");
            return request;
        }).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("test"));
    }

    @Test
    public void testGetExistingAccount() throws Exception {
        Set<Role> authorities = new HashSet<>();
        authorities.add(Role.ADMINISTRADOR);

        UsuarioEntity user = new UsuarioEntity();
        user.setLogin("test");
        user.setNombre("john");
        user.setApellido1("doe");
        user.setEmail("john.doe@jhipster.com");
        user.setRoles(authorities);
        when(mockUserService.getUsuarioWithAuthorities()).thenReturn(user);

        restUserMockMvc.perform(get("/api/usuario").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.login").value("test")).andExpect(jsonPath("$.nombre").value("john")).andExpect(jsonPath("$.apellido1").value("doe"))
                .andExpect(jsonPath("$.email").value("john.doe@jhipster.com")).andExpect(jsonPath("$.roles[*]").value(AccountResourceInternalTest.ROL_ADMIN));
    }

    @Test
    public void testGetUnknownAccount() throws Exception {
        when(mockUserService.getUsuarioWithAuthorities()).thenReturn(null);

        restUserMockMvc.perform(get("/api/usuario").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser("save-account")
    public void testSaveAccount() throws Exception {

        UsuarioEntity user = new UsuarioEntity();
        user.setLogin("save-account");
        user.setEmail("save-account@example.com");
        user.setLanguage(Language.SPANISH);
        user.setGender(Gender.MALE);
        userRepository.saveAndFlush(user);

        UsuarioDto userDto = new UsuarioDto();
        userDto.setId(user.getId());
        userDto.setOptLock(user.getOptLock());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setCreatedBy(user.getCreatedBy());
        userDto.setLogin(user.getLogin());
        userDto.setNombre("firstname");
        userDto.setApellido1("lastname1");
        userDto.setApellido2("lastname2");
        userDto.setEmail("save-account@example.com");
        userDto.setLastModifiedBy(null);
        userDto.setLastModifiedDate(null);
        userDto.setRoles(mockRolSet(Role.ADMINISTRADOR));
        userDto.setLanguage(Language.CATALAN);
        userDto.setGender(Gender.FEMALE);

        restMvc.perform(put("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(userDto))).andExpect(status().isOk());

        UsuarioEntity updatedUser = userRepository.findOneByLogin(user.getLogin()).orElse(null);
        assertThat(updatedUser.getNombre()).isEqualTo(userDto.getNombre());
        assertThat(updatedUser.getApellido1()).isEqualTo(userDto.getApellido1());
        assertThat(updatedUser.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(updatedUser.getRoles()).size().isEqualTo(1);
    }

    @Test
    @WithMockUser("save-existing-email")
    public void testSaveExistingEmail() throws Exception {

        UsuarioEntity user = new UsuarioEntity();
        user.setLogin("save-existing-email");
        user.setEmail("save-existing-email@example.com");
        user.setLanguage(Language.CATALAN);
        user.setGender(Gender.MALE);

        userRepository.saveAndFlush(user);
        UsuarioEntity anotherUser = new UsuarioEntity();
        anotherUser.setLogin("save-existing-email2");
        anotherUser.setEmail("save-existing-email2@example.com");
        anotherUser.setLanguage(Language.SPANISH);
        anotherUser.setGender(Gender.MALE);
        userRepository.saveAndFlush(anotherUser);

        UsuarioDto userDto = new UsuarioDto();
        userDto.setId(user.getId());
        userDto.setOptLock(user.getOptLock());
        userDto.setLogin(user.getLogin());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setCreatedBy(user.getCreatedBy());
        userDto.setNombre("firstname");
        userDto.setApellido1("lastname1");
        userDto.setApellido2("lastname2");
        userDto.setEmail("save-existing-email2@example.com");
        userDto.setLanguage(Language.SPANISH);
        userDto.setGender(Gender.MALE);
        restMvc.perform(put("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(userDto))).andExpect(status().isOk());

        UsuarioEntity updatedUser = userRepository.findOneByLogin("save-existing-email").orElse(null);
        assertThat(updatedUser.getEmail()).isEqualTo("save-existing-email2@example.com");
    }

    @Test
    @WithMockUser("save-existing-email-and-login")
    public void testSaveExistingEmailAndLogin() throws Exception {

        UsuarioEntity user = new UsuarioEntity();
        user.setLogin("save-existing-email-and-login");
        user.setEmail("save-existing-email-and-login@example.com");
        user.setLanguage(Language.CATALAN);
        user.setGender(Gender.MALE);
        userRepository.saveAndFlush(user);

        UsuarioDto userDto = new UsuarioDto();
        userDto.setId(user.getId());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setCreatedBy(user.getCreatedBy());
        userDto.setOptLock(user.getOptLock());
        userDto.setLogin(user.getLogin());
        userDto.setNombre("firstname");
        userDto.setApellido1("lastname1");
        userDto.setApellido2("lastname2");
        userDto.setEmail("save-existing-email-and-login@example.com");
        userDto.setLastModifiedBy(null);
        userDto.setLastModifiedDate(null);
        userDto.setRoles(null);
        userDto.setLanguage(Language.CATALAN);
        userDto.setGender(Gender.MALE);
        restMvc.perform(put("/api/usuarios").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(userDto))).andExpect(status().isOk());

        UsuarioEntity updatedUser = userRepository.findOneByLogin("save-existing-email-and-login").orElse(null);
        assertThat(updatedUser.getEmail()).isEqualTo("save-existing-email-and-login@example.com");
    }
}
