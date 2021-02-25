package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalUserService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalUserDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserMapper;
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
 * @see ExternalUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class ExternalUserResourceExtTest {

    private static final String ENDPOINT_URL = "/api/ext_users";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String DEFAULT_PASSWORD = "doe";
    private static final String DEFAULT_NOMBRE = "john";
    private static final String DEFAULT_PRIMER_APELLIDO = "doe";

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Autowired
    private ExternalUserMapper externalUserMapper;

  /*  @Autowired
    private MailService mailService;  // TODO pending confirmation by email
*/
    @Autowired
    private ExternalUserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMockMvc;

    private ExternalUserEntity newUser;
    private ExternalUserEntity existingUser;

    @Autowired
    private AuditEventPublisher auditPublisher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExternalUserResource userResource = new ExternalUserResource(externalUserRepository, userService, externalUserMapper, auditPublisher);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    public static ExternalUserEntity createEntity() {
        ExternalUserEntity user = new ExternalUserEntity();
        user.setEmail(DEFAULT_EMAIL);
        user.setNombre(DEFAULT_NOMBRE);
        user.setApellido1(DEFAULT_PRIMER_APELLIDO);
        user.setGender(Gender.MALE);
        user.setLanguage(Language.SPANISH);
        user.setPassword(DEFAULT_PASSWORD);
        return user;
    }

    @Before
    public void initTest() {
        newUser = createEntity();
        existingUser = createEntity();
        em.persist(existingUser);
    }

  /*  @Test
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = externalUserRepository.findAll().size();


        ExternalUserDto source = new ExternalUserDto();
        source.updateFrom(externalUserMapper.toDto(newUser));

        restUserMockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(source))).andExpect(status().isCreated());

        // Validate the User in the database
        List<ExternalUserEntity> userList = externalUserRepository.findAll().stream().sorted((u1, u2) -> u2.getId().compareTo(u1.getId())).collect(Collectors.toList());
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalUserEntity testUser = userList.get(0);
        assertThat(testUser.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUser.getApellido1()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }
   */ // TODO Currently this test is failing. Will be improved.

   /* @Test
    public void createUserWithExistingId() throws Exception {
        externalUserRepository.save(newUser);
        int databaseSizeBeforeCreate = externalUserRepository.findAll().size();

        ExternalUserEntity userWithExistingId = externalUserRepository.findOne(newUser.getId());
        userWithExistingId.setEmail("anothermail@localhost");

        ExternalUserDto source = new ExternalUserDto();
        source.updateFrom(externalUserMapper.toDto(userWithExistingId));

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(source))).andExpect(status().isBadRequest());

        // Validate the User in the database
        List<ExternalUserEntity> userList = externalUserRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getUser() throws Exception {
        // Initialize the database
        externalUserRepository.save(newUser);

        // Get the user
        restUserMockMvc.perform(get("/api/usuarios/{email}", newUser.getEmail())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.email").value(newUser.getEmail())).andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE)).andExpect(jsonPath("$.apellido1").value(DEFAULT_PRIMER_APELLIDO));
    }

    @Test
    public void getNonExistingUser() throws Exception {
        restUserMockMvc.perform(get("/api/usuarios/unknown")).andExpect(status().isNotFound());
    }

    @Test
    public void updateUserProperties() throws Exception {
        ExternalUserEntity anotherUser = new ExternalUserEntity();
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setNombre("java");
        anotherUser.setApellido1("hipster");
        anotherUser.setGender(Gender.MALE);
        anotherUser.setLanguage(Language.SPANISH);
        externalUserRepository.saveAndFlush(anotherUser);

        // Update the user
        ExternalUserEntity updatedUser = externalUserRepository.findOne(existingUser.getId());

        ExternalUserDto source = new ExternalUserDto();
        source.updateFrom(externalUserMapper.toDto(updatedUser));
        restUserMockMvc.perform(put(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(source))).andExpect(status().isBadRequest());

        ExternalUserDto user = externalUserMapper.toDto(externalUserRepository.findOneByEmail(anotherUser.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not present by email")));
        user.setEmail("test@t");
        user.setApellido2("hipster2");
        user.setGender(Gender.FEMALE);
        user.setLanguage(Language.CATALAN);
        user.setPhoneNumber("600112233");

        source.updateFrom(user);
        restUserMockMvc.perform(put(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(source))).andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@t"))
                .andExpect(jsonPath("$.gender").value("FEMALE"))
                .andExpect(jsonPath("$.phoneNumber").value("600112233"));
    }

    @Test
    public void testUserFromId() {
        assertThat(externalUserRepository.findOne(existingUser.getId()).getId()).isEqualTo(existingUser.getId());
    }
*/ // TODO At the moment we focus on the creation of the user

    @Test
    public void testUserToUserDto() {
        ExternalUserDto externalUserDto = externalUserMapper.toDto(newUser);
        assertThat(externalUserDto.getId()).isEqualTo(newUser.getId());
        assertThat(externalUserDto.getNombre()).isEqualTo(newUser.getNombre());
        assertThat(externalUserDto.getApellido1()).isEqualTo(newUser.getApellido1());
        assertThat(externalUserDto.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(externalUserDto.getPassword()).isEqualTo(newUser.getPassword());
        assertThat(externalUserDto.getCreatedBy()).isEqualTo(newUser.getCreatedBy());
        assertThat(externalUserDto.getCreatedDate()).isEqualTo(newUser.getCreatedDate());
        assertThat(externalUserDto.getLastModifiedBy()).isEqualTo(newUser.getLastModifiedBy());
        assertThat(externalUserDto.getLastModifiedDate()).isEqualTo(newUser.getLastModifiedDate());
        assertThat(externalUserDto.toString()).isNotNull();
    }
}
