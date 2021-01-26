package es.gobcan.istac.statistical.operations.roadmap.internal.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import es.gobcan.istac.statistical.operations.roadmap.StatisticalOperationsRoadmapRestTestApp;
import es.gobcan.istac.statistical.operations.roadmap.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.UsuarioEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.Rol;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ExceptionTranslator;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.UsuarioRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.MailService;
import es.gobcan.istac.statistical.operations.roadmap.core.service.UsuarioService;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.UsuarioDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.UsuarioMapper;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.resources.UsuarioResource;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.TestUtil;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.vm.ManagedUserVM;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UsuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticalOperationsRoadmapRestTestApp.class)
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
    UsuarioMapper usuarioMapper;

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

    private Set<Rol> mockRolesDto() {
        return Stream.of(Rol.ADMIN).collect(Collectors.toSet());
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

        Set<Rol> authorities = mockRolesDto();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        UsuarioDto source = usuarioMapper.toDto(newUser);
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
        UsuarioDto source = usuarioMapper.toDto(userWithExistingId);
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
    public void updateUserExistingLogin() throws Exception {

        UsuarioEntity anotherUser = new UsuarioEntity();
        anotherUser.setLogin("jhipster");
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setNombre("java");
        anotherUser.setApellido1("hipster");
        userRepository.save(anotherUser);

        // Update the user
        UsuarioEntity updatedUser = userRepository.findOne(existingUser.getId());

        //@formatter:off
        ManagedUserVM managedUserVM = new ManagedUserVM();
        UsuarioDto source = usuarioMapper.toDto(updatedUser);
        source.setLogin(anotherUser.getLogin());
        managedUserVM.updateFrom(source);
        //@formatter:on
        restUserMockMvc.perform(put(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(managedUserVM))).andExpect(status().isBadRequest());
    }

    @Test
    public void testUserFromId() {
        assertThat(userMapper.fromId(existingUser.getId()).getId()).isEqualTo(existingUser.getId());
        assertThat(userMapper.fromId(null)).isNull();
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
