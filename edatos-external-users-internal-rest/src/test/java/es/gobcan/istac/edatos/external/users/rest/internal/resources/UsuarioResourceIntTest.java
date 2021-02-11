package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.UsuarioRepository;
import es.gobcan.istac.edatos.external.users.core.service.MailService;
import es.gobcan.istac.edatos.external.users.core.service.UsuarioService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.UsuarioDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.UsuarioMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.TestUtil;
import es.gobcan.istac.edatos.external.users.rest.common.vm.ManagedUserVM;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UsuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class UsuarioResourceIntTest {

    private static final String ENDPOINT_URL = "/api/usuarios";

    private static final String DEFAULT_LOGIN_NEW_USER = "johndoe";
    private static final String DEFAULT_LOGIN_EXISTING_USER = "alice";
    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String DEFAULT_NOMBRE = "john";
    private static final String DEFAULT_PRIMER_APELLIDO = "doe";

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private UsuarioMapper userMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMockMvc;

    private UsuarioEntity newUser;
    private UsuarioEntity existingUser;

    @Autowired
    private AuditEventPublisher auditPublisher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsuarioResource userResource = new UsuarioResource(userRepository, mailService, userService, userMapper, auditPublisher);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    private Set<Role> mockRolesDto() {
        return Stream.of(Role.ADMINISTRADOR).collect(Collectors.toSet());
    }

    /**
     * Create a User.
     * This is a static method, as tests for other entities might also need it, if
     * they test an entity which has a required relationship to the User entity.
     */
    public static UsuarioEntity createEntity(String login) {
        UsuarioEntity user = new UsuarioEntity();
        user.setLogin(login);
        user.setEmail(DEFAULT_EMAIL);
        user.setNombre(DEFAULT_NOMBRE);
        user.setApellido1(DEFAULT_PRIMER_APELLIDO);
        user.setGender(Gender.MALE);
        user.setLanguage(Language.SPANISH);
        return user;
    }

    @Before
    public void initTest() {
        newUser = createEntity(DEFAULT_LOGIN_NEW_USER);
        existingUser = createEntity(DEFAULT_LOGIN_EXISTING_USER);
        em.persist(existingUser);
    }

    @Test
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        Set<Role> authorities = mockRolesDto();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        UsuarioDto source = userMapper.toDto(newUser);
        source.setRoles(authorities);
        managedUserVM.updateFrom(source);

        restUserMockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(managedUserVM))).andExpect(status().isCreated());

        // Validate the User in the database
        List<UsuarioEntity> userList = userRepository.findAll().stream().sorted((u1, u2) -> u2.getId().compareTo(u1.getId())).collect(Collectors.toList());
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioEntity testUser = userList.get(0);
        assertThat(testUser.getLogin()).isEqualTo(DEFAULT_LOGIN_NEW_USER);
        assertThat(testUser.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUser.getApellido1()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    public void createUserWithExistingId() throws Exception {
        userRepository.save(newUser);
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        UsuarioEntity userWithExistingId = userRepository.findOne(newUser.getId());
        userWithExistingId.setLogin("anotherlogin");
        userWithExistingId.setEmail("anothermail@localhost");

        ManagedUserVM managedUserVM = new ManagedUserVM();
        UsuarioDto source = userMapper.toDto(userWithExistingId);
        managedUserVM.updateFrom(source);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(managedUserVM))).andExpect(status().isBadRequest());

        // Validate the User in the database
        List<UsuarioEntity> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllUsers() throws Exception {
        // Initialize the database
        userRepository.save(newUser);

        // Get all the users
        restUserMockMvc.perform(get("/api/usuarios?sort=id,desc").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN_NEW_USER))).andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
                .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_PRIMER_APELLIDO))).andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    public void getUser() throws Exception {
        // Initialize the database
        userRepository.save(newUser);

        // Get the user
        restUserMockMvc.perform(get("/api/usuarios/{login}", newUser.getLogin())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.login").value(newUser.getLogin())).andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE)).andExpect(jsonPath("$.apellido1").value(DEFAULT_PRIMER_APELLIDO))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    public void getNonExistingUser() throws Exception {
        restUserMockMvc.perform(get("/api/usuarios/unknown")).andExpect(status().isNotFound());
    }

    @Test
    public void updateUserProperties() throws Exception {
        UsuarioEntity anotherUser = new UsuarioEntity();
        anotherUser.setLogin("jhipster");
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setNombre("java");
        anotherUser.setApellido1("hipster");
        anotherUser.setGender(Gender.MALE);
        anotherUser.setLanguage(Language.SPANISH);
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        UsuarioEntity updatedUser = userRepository.findOne(existingUser.getId());

        ManagedUserVM managedUserVM = new ManagedUserVM();
        UsuarioDto source = userMapper.toDto(updatedUser);
        source.setLogin(anotherUser.getLogin());
        managedUserVM.updateFrom(source);
        restUserMockMvc.perform(put(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(managedUserVM))).andExpect(status().isBadRequest());

        UsuarioDto user = userMapper.toDto(userRepository.findOneByLogin(anotherUser.getLogin()).orElseThrow(() -> new IllegalArgumentException("User not present by login")));
        user.setLogin("myNewLogin2");
        user.setEmail("test@t");
        user.setApellido2("hipster2");
        user.setGender(Gender.FEMALE);
        user.setLanguage(Language.CATALAN);
        user.setPhoneNumber("600112233");
        user.setOrganization("ACME Corporation");
        // userRepository.saveAndFlush(anotherUser);

        managedUserVM = new ManagedUserVM();
        managedUserVM.updateFrom(user);
        restUserMockMvc.perform(put(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(managedUserVM))).andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("mynewlogin2"))
                .andExpect(jsonPath("$.gender").value("FEMALE"))
                .andExpect(jsonPath("$.organization").value("ACME Corporation"))
                .andExpect(jsonPath("$.phoneNumber").value("600112233"));
    }

    @Test
    public void testUserFromId() {
        assertThat(userRepository.findOne(existingUser.getId()).getId()).isEqualTo(existingUser.getId());
    }

    @Test
    public void testUserToUserDto() {
        UsuarioDto userDto = userMapper.toDto(newUser);
        assertThat(userDto.getId()).isEqualTo(newUser.getId());
        assertThat(userDto.getLogin()).isEqualTo(newUser.getLogin());
        assertThat(userDto.getNombre()).isEqualTo(newUser.getNombre());
        assertThat(userDto.getApellido1()).isEqualTo(newUser.getApellido1());
        assertThat(userDto.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(userDto.getCreatedBy()).isEqualTo(newUser.getCreatedBy());
        assertThat(userDto.getCreatedDate()).isEqualTo(newUser.getCreatedDate());
        assertThat(userDto.getLastModifiedBy()).isEqualTo(newUser.getLastModifiedBy());
        assertThat(userDto.getLastModifiedDate()).isEqualTo(newUser.getLastModifiedDate());
        assertEquals(userDto.getRoles(), newUser.getRoles());
        assertThat(userDto.toString()).isNotNull();
    }
}
