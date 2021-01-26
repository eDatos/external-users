package es.gobcan.istac.edatos.external.users.internal.rest;

import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.LOCALISED_STRING;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.LOCALISED_STRING_LABEL_UPDATE;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.LOCALISED_STRING_LOCALE_UPDATE;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_CODE_1;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_CODE_2;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_CODE_3;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.NEED_CODE_UPDATE;
import static es.gobcan.istac.edatos.external.users.internal.rest.util.RoadmapDataTestsUtils.createEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.siemac.edatos.core.common.constants.shared.ExceptionCodeConstants;
import org.siemac.edatos.core.common.util.GeneratorUrnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.internal.rest.dto.NeedDto;
import es.gobcan.istac.edatos.external.users.internal.rest.mapper.NeedMapper;
import es.gobcan.istac.edatos.external.users.StatisticalOperationsRoadmapRestTestApp;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.NeedEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.edatos.external.users.core.repository.NeedRepository;
import es.gobcan.istac.edatos.external.users.core.service.NeedService;
import es.gobcan.istac.edatos.external.users.internal.rest.resources.NeedResource;
import es.gobcan.istac.edatos.external.users.internal.rest.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticalOperationsRoadmapRestTestApp.class)
@Transactional
public class NeedResourceInTest {

    private static final String ENDPOINT_URL = "/api/needs";

    @Autowired
    private NeedRepository needRepository;

    @Autowired
    private NeedService needService;

    @Autowired
    private NeedMapper needMapper;

    @Autowired
    private AuditEventPublisher auditEventPublisher;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    @Autowired
    protected PlatformTransactionManager platformTransactionManager;

    private MockMvc restNeedMockMvc;

    private NeedEntity newNeed;

    private NeedEntity existingNeed;

    private NeedEntity internalPublishedNeed;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NeedResource needResource = new NeedResource(needService, needMapper, auditEventPublisher);
        // @formatter:off
        this.restNeedMockMvc = MockMvcBuilders.standaloneSetup(needResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter)
                .build();
        // @formatter:on
    }

    @Before
    public void initTest() {
        newNeed = createEntity(NEED_CODE_1, LOCALISED_STRING, null);

        existingNeed = createEntity(NEED_CODE_2, LOCALISED_STRING, ProcStatusEnum.DRAFT);
        em.persist(existingNeed);

        internalPublishedNeed = createEntity(NEED_CODE_3, LOCALISED_STRING, ProcStatusEnum.INTERNALLY_PUBLISHED);
        em.persist(internalPublishedNeed);

        em.flush();
    }

    @Test
    @Transactional
    public void createNeed() throws Exception {
        int databaseSizeBeforeCreate = needRepository.findAll().size();

        NeedDto needDto = needMapper.toDto(newNeed);

        // @formatter:off
        restNeedMockMvc.perform(post(ENDPOINT_URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(needDto)))
            .andExpect(status().isCreated());        
        // @formatter:on

        List<NeedEntity> needs = needRepository.findAll();
        Assertions.assertThat(needs).hasSize(databaseSizeBeforeCreate + 1);
        NeedEntity testNeed = needs.get(needs.size() - 1);
        Assertions.assertThat(testNeed.getCode()).isEqualTo(NEED_CODE_1);
        Assertions.assertThat(testNeed.getUrn()).isEqualTo(GeneratorUrnUtils.generateSiemacStatisticalNeedUrn(NEED_CODE_1));
        Assertions.assertThat(testNeed.getTitle().getTexts().size()).isEqualTo(2);
        Assertions.assertThat(testNeed.getUuid()).isNotEmpty();
        Assertions.assertThat(testNeed.getProcStatus()).isEqualTo(ProcStatusEnum.DRAFT);
    }

    @Test
    @Transactional
    public void createExistingNeed() throws Exception {
        NeedDto needDto = needMapper.toDto(existingNeed);

        // @formatter:off
        restNeedMockMvc.perform(post(ENDPOINT_URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(needDto)))
            .andExpect(status().isBadRequest());
        // @formatter:on
    }

    @Test
    @Transactional
    public void createNeedRequiredMetadataError() throws Exception {
        {
            NeedEntity needEntity = createEntity(null, LOCALISED_STRING, ProcStatusEnum.DRAFT);

            NeedDto needDto = needMapper.toDto(needEntity);

            // @formatter:off
            ResultActions resultActions = restNeedMockMvc.perform(post(ENDPOINT_URL)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(needDto)));
            
            resultActions.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorItems").isNotEmpty())
            .andExpect(jsonPath("$.errorItems.[0].code").value(ExceptionCodeConstants.METADATA_REQUIRED))
            .andExpect(jsonPath("$.errorItems.[0].params").value(Matchers.hasItems(ServiceExceptionParameters.NEED_CODE)));
            // @formatter:on
        }
        {
            NeedEntity needEntity = createEntity(NEED_CODE_1, null, ProcStatusEnum.DRAFT);

            NeedDto needDto = needMapper.toDto(needEntity);

            // @formatter:off
            ResultActions resultActions = restNeedMockMvc.perform(post(ENDPOINT_URL)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(needDto)));
            
            resultActions.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorItems").isNotEmpty())
            .andExpect(jsonPath("$.errorItems.[0].code").value(ExceptionCodeConstants.METADATA_REQUIRED))
            .andExpect(jsonPath("$.errorItems.[0].params").value(Matchers.hasItems(ServiceExceptionParameters.NEED_TITLE)));
            // @formatter:on
        }
        {
            NeedEntity needEntity = createEntity("NON_VALID_CODE!", LOCALISED_STRING, ProcStatusEnum.DRAFT);

            NeedDto needDto = needMapper.toDto(needEntity);

            // @formatter:off
            ResultActions resultActions = restNeedMockMvc.perform(post(ENDPOINT_URL)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(needDto)));
            
            resultActions.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorItems").isNotEmpty())
            .andExpect(jsonPath("$.errorItems.[0].code").value(ExceptionCodeConstants.METADATA_INCORRECT))
            .andExpect(jsonPath("$.errorItems.[0].params").value(Matchers.hasItems(ServiceExceptionParameters.NEED_CODE)));
            // @formatter:on
        }
    }

    @Test
    @Transactional
    public void updateNeed() throws Exception {
        int databaseSizeBeforeCreate = needRepository.findAll().size();

        NeedEntity updatedNeed = needRepository.findOne(existingNeed.getId());
        int titleNumber = updatedNeed.getTitle().getTexts().size();
        Assertions.assertThat(updatedNeed.getIdentificationDate()).isNull();
        Assertions.assertThat(updatedNeed.getUuid()).isNotBlank();

        updatedNeed.setCode(NEED_CODE_UPDATE);
        updatedNeed.getTitle().addText(new LocalisedStringVO(LOCALISED_STRING_LABEL_UPDATE, LOCALISED_STRING_LOCALE_UPDATE));
        updatedNeed.setIdentificationDate(Instant.now());

        NeedDto needDto = needMapper.toDto(updatedNeed);

        // @formatter:off
        restNeedMockMvc.perform(put(ENDPOINT_URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(needDto)))
            .andExpect(status().isOk());
        // @formatter:on

        List<NeedEntity> needs = needRepository.findAll();
        Assertions.assertThat(needs).hasSize(databaseSizeBeforeCreate);

        NeedEntity testNeed = needRepository.findOne(existingNeed.getId());
        Assertions.assertThat(testNeed.getCode()).isEqualTo(NEED_CODE_UPDATE);
        Assertions.assertThat(testNeed.getUrn()).isEqualTo(GeneratorUrnUtils.generateSiemacStatisticalNeedUrn(NEED_CODE_UPDATE));
        Assertions.assertThat(testNeed.getTitle().getTexts().size()).isEqualTo(titleNumber + 1);
        Assertions.assertThat(testNeed.getIdentificationDate()).isNotNull();
        Assertions.assertThat(testNeed.getUuid()).isEqualTo(updatedNeed.getUuid());
    }

    @Test
    @Transactional
    public void updateNonExistingNeed() throws Exception {
        NeedDto needDto = needMapper.toDto(newNeed);

        // @formatter:off
        restNeedMockMvc.perform(put(ENDPOINT_URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(needDto)))
            .andExpect(status().isBadRequest());
        // @formatter:on
    }

    @Test
    @Transactional
    public void deleteNeed() throws Exception {
        int databaseSizeBeforeDelete = needRepository.findAll().size();

        // @formatter:off
        restNeedMockMvc.perform(
                delete(ENDPOINT_URL + "/{id}", existingNeed.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
        // @formatter:on

        List<NeedEntity> actorList = needRepository.findAll();
        Assertions.assertThat(actorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void deleteNonExistingNeed() throws Exception {
        // @formatter:off
        restNeedMockMvc.perform(
                delete(ENDPOINT_URL + "/{id}", 99999).accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());
        // @formatter:on
    }

    @Test
    @Transactional
    public void deleteNonDraftNeed() throws Exception {

        // @formatter:off
        ResultActions resultActions = restNeedMockMvc.perform(
                delete(ENDPOINT_URL + "/{id}", internalPublishedNeed.getId()).accept(TestUtil.APPLICATION_JSON_UTF8));
        
        resultActions.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.errorItems").isNotEmpty())
        .andExpect(jsonPath("$.errorItems.[0].code").value("exception.roadmap.invalid.proc_status"))
        .andExpect(jsonPath("$.errorItems.[0].params").value(Matchers.hasItems(ProcStatusEnum.DRAFT.toString())));
        // @formatter:on
    }

    @Test
    @Transactional
    public void getAllNeeds() throws Exception {
        // @formatter:off
        restNeedMockMvc.perform(
                get(ENDPOINT_URL + "?sort=id,desc"))
            //.andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(Matchers.hasItem(existingNeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(Matchers.hasItems(NEED_CODE_2)))
            .andExpect(jsonPath("$.[*].urn").value(Matchers.hasItem(GeneratorUrnUtils.generateSiemacStatisticalNeedUrn(NEED_CODE_2))));
        // @formatter:on
    }

    @Test
    @Transactional
    public void getNeed() throws Exception {
        // @formatter:off
        restNeedMockMvc.perform(
                get(ENDPOINT_URL + "/{id}", existingNeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(existingNeed.getId()))
            .andExpect(jsonPath("$.code").value(NEED_CODE_2))
            .andExpect(jsonPath("$.urn").value(GeneratorUrnUtils.generateSiemacStatisticalNeedUrn(NEED_CODE_2)));
        // @formatter:on
    }

    @Test
    @Transactional
    public void getNonExistingNeed() throws Exception {
        // @formatter:off
        restNeedMockMvc.perform(
                get(ENDPOINT_URL + "/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
        // @formatter:on
    }
}
