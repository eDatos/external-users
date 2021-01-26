package es.gobcan.istac.edatos.external.users.internal.rest;

import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.LOCALISED_STRING;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_STATE_CODE_1;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_STATE_CODE_2;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_STATE_CODE_3;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_STATE_CODE_NO_EXIST;
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

import es.gobcan.istac.edatos.external.users.internal.rest.mapper.NeedStateMapper;
import es.gobcan.istac.edatos.external.users.StatisticalOperationsRoadmapRestTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.NeedStateEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.NeedStateRepository;
import es.gobcan.istac.edatos.external.users.core.service.NeedStateService;
import es.gobcan.istac.edatos.external.users.internal.rest.resources.NeedStateResource;
import es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticalOperationsRoadmapRestTestApp.class)
@Transactional
public class NeedStateResourceInTest {

    private static final String ENDPOINT_URL = "/api/needStates";

    @Autowired
    NeedStateRepository needStateRepository;

    @Autowired
    NeedStateService needStateService;

    @Autowired
    NeedStateMapper needStateMapper;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    private MockMvc restNeedStateMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NeedStateResource needStateResource = new NeedStateResource(needStateService, needStateMapper);
        // @formatter:off
        this.restNeedStateMockMvc = MockMvcBuilders.standaloneSetup(needStateResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter)
                .build();
        // @formatter:on
    }

    @Before
    public void initTest() {
        NeedStateEntity needStateEntity1 = RoadmapDataTestsUtils.createNeedStateEntity(NEED_STATE_CODE_1, LOCALISED_STRING);
        em.persist(needStateEntity1);

        NeedStateEntity needStateEntity2 = RoadmapDataTestsUtils.createNeedStateEntity(NEED_STATE_CODE_2, LOCALISED_STRING);
        em.persist(needStateEntity2);

        NeedStateEntity needStateEntity3 = RoadmapDataTestsUtils.createNeedStateEntity(NEED_STATE_CODE_3, LOCALISED_STRING);
        em.persist(needStateEntity3);
    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        Assertions.assertThat(needStateRepository.findAll()).hasSize(3);

        // @formatter:off
        restNeedStateMockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].code").value(
                    Matchers.hasItems(NEED_STATE_CODE_1, NEED_STATE_CODE_2, NEED_STATE_CODE_3)));
        // @formatter:on
    }

    @Test
    @Transactional
    public void getNeedTypeByCode() throws Exception {

        // @formatter:off
        restNeedStateMockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_URL + "/{code}", NEED_STATE_CODE_2))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(NEED_STATE_CODE_2));
        // @formatter:on
    }

    @Test
    @Transactional
    public void getNonExistingNeedTypeByCode() throws Exception {
        // @formatter:off
        restNeedStateMockMvc.perform(
                get(ENDPOINT_URL + "/{code}", NEED_STATE_CODE_NO_EXIST))
            .andExpect(status().isNotFound());
        // @formatter:on
    }
}