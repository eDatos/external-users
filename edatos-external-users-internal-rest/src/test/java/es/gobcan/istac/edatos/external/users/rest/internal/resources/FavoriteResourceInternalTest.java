package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Treatment;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.repository.FavoriteRepository;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.CategoryMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserAccountMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.OperationMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class FavoriteResourceInternalTest {

    private static final String ENDPOINT_URL = FavoriteResource.BASE_URL;
    private static final String LOCALE = "es";

    @Autowired
    private FavoriteResource favoriteResource;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private ExternalUserAccountMapper externalUserAccountMapper;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;

    private ExternalUserEntity user1;

    private ExternalUserEntity user2;

    private CategoryEntity category1;

    private OperationEntity operation1;

    private FavoriteEntity favorite1;

    private FavoriteEntity favorite2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(favoriteResource).setControllerAdvice(exceptionTranslator).setCustomArgumentResolvers(pageableArgumentResolver).build();
    }

    @Before
    public void initializeDb() {
        user1 = new ExternalUserEntity();
        user1.setName("Montserrat");
        user1.setSurname1("Pons");
        user1.setSurname2("Ferrer");
        user1.setPassword("password1");
        user1.setEmail("user1@gmail.com");
        user1.setTreatment(Treatment.LADY);
        user1.setLanguage(Language.CATALAN);
        externalUserRepository.saveAndFlush(user1);

        user2 = new ExternalUserEntity();
        user2.setName("Emma");
        user2.setSurname1("García");
        user2.setSurname2("Torres");
        user2.setPassword("password1");
        user2.setEmail("user2@gmail.com");
        user2.setTreatment(Treatment.LADY);
        user2.setLanguage(Language.CATALAN);
        externalUserRepository.saveAndFlush(user2);

        LocalisedStringVO locStr = new LocalisedStringVO();
        locStr.setLabel("Categoría 1");
        locStr.setLocale(LOCALE);

        InternationalStringVO intStr = new InternationalStringVO();
        intStr.addText(locStr);

        category1 = new CategoryEntity();
        category1.setName(intStr);
        category1.setUri("uri:000");
        category1.setUrn("urn:000");
        category1.setCode("000");
        categoryRepository.saveAndFlush(category1);

        locStr = new LocalisedStringVO();
        locStr.setLabel("Operation 1");
        locStr.setLocale(LOCALE);

        intStr = new InternationalStringVO();
        intStr.addText(locStr);

        operation1 = new OperationEntity();
        operation1.setName(intStr);
        operation1.setCode("o:000");
        operation1.setCategory(category1);
        operation1.setProcStatus(ProcStatus.INTERNALLY_PUBLISHED);
        operation1.setStatus(Status.PRODUCTION);
        operationRepository.saveAndFlush(operation1);

        favorite1 = new FavoriteEntity();
        favorite1.setExternalUser(user1);
        favorite1.setCategory(category1);
        favoriteRepository.saveAndFlush(favorite1);

        favorite2 = new FavoriteEntity();
        favorite2.setExternalUser(user2);
        favorite2.setOperation(operation1);
        favoriteRepository.saveAndFlush(favorite2);
    }

    @Test
    public void testGetFavoriteById() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "/" + favorite1.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(favorite1.getId().intValue())))
                .andExpect(jsonPath("$.category.name.texts[0].label", is(favorite1.getCategory().getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testGetListOfFavorites() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].category.name.texts[0].label", is(category1.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$[1].operation.name.texts[0].label", is(operation1.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testPostFavorite() throws Exception {
        FavoriteDto dto = new FavoriteDto();
        dto.setExternalUser(externalUserAccountMapper.toDto(user1));
        dto.setOperation(operationMapper.toDto(operation1));

        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.operation.name.texts[0].label", is(operation1.getName().getLocalisedLabel(LOCALE)))).andExpect(jsonPath("$.externalUser.name", is(user1.getName())));
        assertTrue(favoriteRepository.findAllByExternalUserOrderByCreatedDate(user1).stream().anyMatch(favorite -> favorite.getOperation() != null && favorite.getOperation().equals(operation1)));
    }

    @Test
    public void testDeleteFavorite() throws Exception {
        assertNotNull(favoriteRepository.findOne(favorite1.getId()));
        this.mockMvc.perform(delete(ENDPOINT_URL + "/" + favorite1.getId())).andExpect(status().isNoContent());
        assertNull(favoriteRepository.findOne(favorite1.getId()));
    }

    @Test
    public void testFavoriteCantHaveOperationAndCategoryAtTheSameTime() throws Exception {
        FavoriteDto dto = new FavoriteDto();
        dto.setExternalUser(externalUserAccountMapper.toDto(user1));
        dto.setOperation(operationMapper.toDto(operation1));
        dto.setCategory(categoryMapper.toDto(category1));

        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", equalToIgnoringCase("exception.external_users.favorite.cannot_set_both_operation_and_category")));
    }

    @Test
    public void testUserCantHaveTheSameFavoriteTwice() throws Exception {
        FavoriteDto dto = new FavoriteDto();
        dto.setExternalUser(externalUserAccountMapper.toDto(user1));
        dto.setCategory(categoryMapper.toDto(category1));

        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetFavoriteByUser() throws Exception {
        this.mockMvc.perform(get(String.format("%s?query=USER_ID EQ '%s'", ENDPOINT_URL, user1.getId()))).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].category.name.texts[0].label", is(category1.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void sortFavoriteByCreatedDate() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "?sort=CREATED_DATE,desc")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(favorite2.getId().intValue()))).andExpect(jsonPath("$[1].id", is(favorite1.getId().intValue())));

        this.mockMvc.perform(get(ENDPOINT_URL + "?sort=CREATED_DATE,asc")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(favorite1.getId().intValue()))).andExpect(jsonPath("$[1].id", is(favorite2.getId().intValue())));
    }
}
