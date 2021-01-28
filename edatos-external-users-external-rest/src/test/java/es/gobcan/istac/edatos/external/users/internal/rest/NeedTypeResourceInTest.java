package es.gobcan.istac.edatos.external.users.internal.rest;

import static es.gobcan.istac.edatos.external.users.internal.rest.util.ExternalUsersDataTestsUtils.LOCALISED_STRING;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.ExternalUsersDataTestsUtils.NEED_TYPE_CODE_1;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.ExternalUsersDataTestsUtils.NEED_TYPE_CODE_2;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.ExternalUsersDataTestsUtils.NEED_TYPE_CODE_3;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.ExternalUsersDataTestsUtils.NEED_TYPE_CODE_NO_EXIST;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.ExternalUsersDataTestsUtils.createNeedTypeEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.NeedTypeEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.NeedTypeRepository;
import es.gobcan.istac.edatos.external.users.core.service.NeedTypeService;
import es.gobcan.istac.edatos.external.users.internal.rest.mapper.NeedTypeMapper;
import es.gobcan.istac.edatos.external.users.internal.rest.resources.NeedTypeResource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class NeedTypeResourceInTest {

    private static final String ENDPOINT_URL = "/api/needTypes";

    @Autowired
    NeedTypeRepository needTypeRepository;

    @Autowired
    NeedTypeService needTypeService;

    @Autowired
    NeedTypeMapper needTypeMapper;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    private MockMvc restNeedTypeMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NeedTypeResource needTypeResource = new NeedTypeResource(needTypeService, needTypeMapper);
        // @formatter:off
        this.restNeedTypeMockMvc = MockMvcBuilders.standaloneSetup(needTypeResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter)
                .build();
        // @formatter:on
    }

    @Before
    public void initTest() {
        NeedTypeEntity needTypeEntity1 = createNeedTypeEntity(NEED_TYPE_CODE_1, LOCALISED_STRING);
        em.persist(needTypeEntity1);

        NeedTypeEntity needTypeEntity2 = createNeedTypeEntity(NEED_TYPE_CODE_2, LOCALISED_STRING);
        em.persist(needTypeEntity2);

        NeedTypeEntity needTypeEntity3 = createNeedTypeEntity(NEED_TYPE_CODE_3, LOCALISED_STRING);
        em.persist(needTypeEntity3);
    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        Assertions.assertThat(needTypeRepository.findAll()).hasSize(3);

        // @formatter:off
        restNeedTypeMockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].code").value(
                    Matchers.hasItems(NEED_TYPE_CODE_1, NEED_TYPE_CODE_2, NEED_TYPE_CODE_3)));
        // @formatter:on
    }

    @Test
    @Transactional
    public void getNeedTypeByCode() throws Exception {

        // @formatter:off
        restNeedTypeMockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_URL + "/{code}", NEED_TYPE_CODE_2))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(NEED_TYPE_CODE_2));
        // @formatter:on
    }

    @Test
    @Transactional
    public void getNonExistingNeedTypeByCode() throws Exception {
        // @formatter:off
        restNeedTypeMockMvc.perform(
                get(ENDPOINT_URL + "/{code}", NEED_TYPE_CODE_NO_EXIST))
            .andExpect(status().isNotFound());
        // @formatter:on
    }
}
