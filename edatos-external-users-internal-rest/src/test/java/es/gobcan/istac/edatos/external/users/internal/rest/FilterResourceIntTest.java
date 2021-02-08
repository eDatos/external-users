package es.gobcan.istac.edatos.external.users.internal.rest;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Gender;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.repository.FilterRepository;
import es.gobcan.istac.edatos.external.users.core.repository.UsuarioRepository;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.internal.rest.resources.FilterResource;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class FilterResourceIntTest {

    private static final String ENDPOINT_URL = "/api/filters";

    @Autowired
    private FilterResource filterResource;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private UsuarioRepository userRepository;

    private MockMvc mockMvc;
    private UsuarioEntity user1;
    private FilterEntity filter1;
    private FilterEntity filter2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(filterResource).build();
    }

    @Before
    public void populateDatabase() {
        user1 = new UsuarioEntity();
        user1.setLogin("user1");
        user1.setGender(Gender.MALE);
        user1.setLanguage(Language.CATALAN);
        userRepository.saveAndFlush(user1);

        filter1 = new FilterEntity();
        filter1.setName("My filter 1");
        filter1.setPermalink(
                "https://www3.gobiernodecanarias.org/istac/statistical-visualizer/visualizer/data.html?permalink=vr0oanlyid8rvs613sya4ysbi");
        filter1.setUser(user1);
        filterRepository.saveAndFlush(filter1);
        filter2 = new FilterEntity();
        filter2.setName("My filter 2");
        filter2.setPermalink(
                "https://www3.gobiernodecanarias.org/istac/statistical-visualizer/visualizer/data.html?permalink=1iehlgx1d7od76vr11r31d11j5o");
        filter2.setUser(user1);
        filterRepository.saveAndFlush(filter2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveTwoFiltersWithTheSameUserAndPermalink() {
        filter2.setPermalink(filter1.getPermalink());
        filterRepository.saveAndFlush(filter2);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveANonUrlPermalink() {
        filter1.setPermalink("not a url");
        filterRepository.saveAndFlush(filter1);
    }

    @Test
    public void testCreateFilter() throws Exception {
        FilterDto dto = new FilterDto();
        //dto.
        this.mockMvc.perform(get(ENDPOINT_URL)).andExpect(status().isOk());
    }

    @Test
    public void testListAllFilters() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name", is("My filter 1")))
                    .andExpect(jsonPath("$[1].name", is("My filter 2")));
    }
}
