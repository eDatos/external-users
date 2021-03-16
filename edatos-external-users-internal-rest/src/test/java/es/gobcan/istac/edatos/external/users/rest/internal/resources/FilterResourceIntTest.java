package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Gender;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.FilterRepository;
import es.gobcan.istac.edatos.external.users.core.repository.UsuarioRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.FilterMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class FilterResourceIntTest {

    private static final String ENDPOINT_URL = "/api/filters";

    private static final String PERMALINK_1 = "https://www3.gobiernodecanarias.org/istac/statistical-visualizer/visualizer/data.html?permalink=1iehlgx1d7od76vr11r31d11j5o";
    private static final String PERMALINK_2 = "https://www3.gobiernodecanarias.org/istac/statistical-visualizer/visualizer/data.html?permalink=vr0oanlyid8rvs613sya4ysbi";

    @Autowired
    private FilterResource filterResource;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private FilterMapper filterMapper;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;
    private UsuarioEntity user1;
    private UsuarioEntity user2;
    private FilterEntity filter1;
    private FilterEntity filter2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(filterResource)
                                      .setControllerAdvice(exceptionTranslator)
                                      .setCustomArgumentResolvers(pageableArgumentResolver)
                                      .build();
    }

    @Before
    public void populateDatabase() {
        user1 = new UsuarioEntity();
        user1.setLogin("user1");
        user1.setGender(Gender.MALE);
        user1.setLanguage(Language.CATALAN);
        userRepository.saveAndFlush(user1);
        user2 = new UsuarioEntity();
        user2.setLogin("user2");
        user2.setGender(Gender.FEMALE);
        user2.setLanguage(Language.CATALAN);
        userRepository.saveAndFlush(user2);

        filter1 = new FilterEntity();
        filter1.setName("My filter 1");
        filter1.setPermalink(PERMALINK_1);
        filter1.setUser(user1);
        filterRepository.saveAndFlush(filter1);
        filter2 = new FilterEntity();
        filter2.setName("My filter 2");
        filter2.setPermalink(PERMALINK_2);
        filter2.setUser(user1);
        filterRepository.saveAndFlush(filter2);
    }

    @Test
    public void testSaveTwoFiltersWithTheSameUserAndPermalink() throws Exception {
        filter2.setPermalink(filter1.getPermalink());
        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8)
                                               .content(TestUtil.convertObjectToJsonBytes(filterMapper.toDto(filter2))))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveANonUrlPermalink() throws Exception {
        filter1.setPermalink("not a url");
        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8)
                                               .content(TestUtil.convertObjectToJsonBytes(filterMapper.toDto(filter1))))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilterFail() throws Exception {
        FilterDto dto = new FilterDto();
        dto.setLogin(user2.getLogin());
        dto.setPermalink(PERMALINK_1);
        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8)
                                               .content(TestUtil.convertObjectToJsonBytes(dto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilterSucceeds() throws Exception {
        FilterDto dto = new FilterDto();
        dto.setLogin(user2.getLogin());
        dto.setPermalink(PERMALINK_1);
        dto.setResourceName("resource name");
        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8)
                                               .content(TestUtil.convertObjectToJsonBytes(dto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is("resource name")));

        dto.setName("filter name");
        dto.setPermalink(PERMALINK_2);
        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8)
                                               .content(TestUtil.convertObjectToJsonBytes(dto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is("filter name")));
    }

    @Test
    public void testCanUpdateFilter() throws Exception {
        FilterDto dto = filterMapper.toDto(filter1);
        dto.setName("My new filter name");

        this.mockMvc.perform(put(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8)
                                              .content(TestUtil.convertObjectToJsonBytes(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("My new filter name")));

        assertThat(filter1.getLastModifiedDate()).isNotEqualTo(dto.getLastModifiedDate());
    }

    @Test
    public void testDeleteFilter() throws Exception {
        this.mockMvc.perform(delete(ENDPOINT_URL + "/" + filter1.getId())).andExpect(status().isNoContent());
        assertThat(filterRepository.findOne(filter1.getId())).isNull();
    }

    @Test
    public void testListAllFilters() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name", is("My filter 1")))
                    .andExpect(jsonPath("$[1].name", is("My filter 2")));
    }

    @Test
    public void testGetFilterByName() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "?query=NAME ILIKE '%filter 2'"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is("My filter 2")));
    }

    @Test
    public void testGetFilterByUserLogin() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "?query=LOGIN EQ 'user1'"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name", is("My filter 1")))
                    .andExpect(jsonPath("$[1].name", is("My filter 2")));

        FilterEntity filter = new FilterEntity();
        filter.setName("User 2 filter");
        filter.setPermalink(PERMALINK_1);
        filter.setUser(user2);
        filterRepository.saveAndFlush(filter);

        this.mockMvc.perform(get(ENDPOINT_URL + "?query=LOGIN EQ 'user2'"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is("User 2 filter")));
    }

    @Test
    public void testSortFiltersByUserLogin() throws Exception {
        filter2.setUser(user2);
        filterRepository.saveAndFlush(filter2);

        this.mockMvc.perform(get(ENDPOINT_URL + "?sort=login,desc"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].login", is("user2")))
                    .andExpect(jsonPath("$[1].login", is("user1")));
    }
}
