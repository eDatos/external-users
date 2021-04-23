package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.util.List;
import java.util.stream.Collectors;

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
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.repository.FavoriteRepository;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.CategoryMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalUserAccountMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.OperationMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.TestUtil;

import static es.gobcan.istac.edatos.external.users.util.InternationalStringUtils.createIntStr;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private FavoriteService favoriteService;

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

    private CategoryEntity category0a;
    private CategoryEntity category0b;
    private CategoryEntity category1;
    private CategoryEntity category2a;
    private CategoryEntity category2b;

    private OperationEntity operation1;
    private OperationEntity operation2;
    private OperationEntity operation3;

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

        category0a = new CategoryEntity();
        category0a.setName(createIntStr(LOCALE, "Categoría abuela 1"));
        category0a.setUri("uri:000a");
        category0a.setUrn("urn:000a");
        category0a.setCode("000a");
        categoryRepository.saveAndFlush(category0a);

        category0b = new CategoryEntity();
        category0b.setName(createIntStr(LOCALE, "Categoría abuela 2"));
        category0b.setUri("uri:000b");
        category0b.setUrn("urn:000b");
        category0b.setCode("000b");
        categoryRepository.saveAndFlush(category0b);

        category1 = new CategoryEntity();
        category1.setName(createIntStr(LOCALE, "Categoría madre"));
        category1.setUri("uri:001");
        category1.setUrn("urn:001");
        category1.setCode("001");
        category1.setParent(category0a);
        categoryRepository.saveAndFlush(category1);

        category2a = new CategoryEntity();
        category2a.setName(createIntStr(LOCALE, "Categoría hija 1"));
        category2a.setUri("uri:002a");
        category2a.setUrn("urn:002a");
        category2a.setCode("002a");
        category2a.setParent(category1);
        categoryRepository.saveAndFlush(category2a);

        category2b = new CategoryEntity();
        category2b.setName(createIntStr(LOCALE, "Categoría hija 2"));
        category2b.setUri("uri:002b");
        category2b.setUrn("urn:002b");
        category2b.setCode("002b");
        category2b.setParent(category1);
        categoryRepository.saveAndFlush(category2b);

        operation1 = new OperationEntity();
        operation1.setName(createIntStr(LOCALE, "Operación 1"));
        operation1.setCode("o:001");
        operation1.setCategory(category0a);
        operation1.setProcStatus(ProcStatus.INTERNALLY_PUBLISHED);
        operation1.setStatus(Status.PRODUCTION);
        operationRepository.saveAndFlush(operation1);

        operation2 = new OperationEntity();
        operation2.setName(createIntStr(LOCALE, "Operación 2"));
        operation2.setCode("o:002");
        operation2.setCategory(category2b);
        operation2.setProcStatus(ProcStatus.INTERNALLY_PUBLISHED);
        operation2.setStatus(Status.PRODUCTION);
        operationRepository.saveAndFlush(operation2);

        operation3 = new OperationEntity();
        operation3.setName(createIntStr(LOCALE, "Operación 3"));
        operation3.setCode("o:003");
        operation3.setCategory(category2a);
        operation3.setProcStatus(ProcStatus.INTERNALLY_PUBLISHED);
        operation3.setStatus(Status.PRODUCTION);
        operationRepository.saveAndFlush(operation3);

        favorite1 = new FavoriteEntity();
        favorite1.setExternalUser(user1);
        favorite1.setOperation(operation1);
        favoriteRepository.saveAndFlush(favorite1);

        favorite2 = new FavoriteEntity();
        favorite2.setExternalUser(user2);
        favorite2.setOperation(operation2);
        favoriteRepository.saveAndFlush(favorite2);
    }

    @Test
    public void testGetFavoriteById() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "/" + favorite1.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(favorite1.getId().intValue())))
                .andExpect(jsonPath("$.operation.name.texts[0].label", is(favorite1.getOperation().getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testGetListOfFavorites() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].operation.name.texts[0].label", is(operation1.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$[1].operation.name.texts[0].label", is(operation2.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testPostFavorite() throws Exception {
        FavoriteDto dto = new FavoriteDto();
        dto.setExternalUser(externalUserAccountMapper.toDto(user1));
        dto.setOperation(operationMapper.toDto(operation3));

        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.operation.name.texts[0].label", is(operation3.getName().getLocalisedLabel(LOCALE)))).andExpect(jsonPath("$.externalUser.name", is(user1.getName())));
        assertTrue(favoriteRepository.findByExternalUser(user1).stream().anyMatch(favorite -> favorite.getOperation() != null && favorite.getOperation().equals(operation3)));
    }

    @Test
    public void testPostCategoryFavoriteRecursivelyMarksAllChildrenAsFavorite() throws Exception {
        FavoriteDto dto = new FavoriteDto();
        dto.setExternalUser(externalUserAccountMapper.toDto(user1));
        dto.setCategory(categoryMapper.toDto(category0a));

        List<FavoriteEntity> favorites = favoriteRepository.findByExternalUser(user1);
        assertEquals(1, favorites.size());
        assertThat(favorites.stream().map(fav -> fav.getCategory() != null ? fav.getCategory() : fav.getOperation()).collect(Collectors.toList()), contains(operation1));

        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isCreated());

        favorites = favoriteRepository.findByExternalUser(user1);
        assertEquals(7, favorites.size());
        assertThat(favorites.stream().map(fav -> fav.getCategory() != null ? fav.getCategory() : fav.getOperation()).collect(Collectors.toList()),
                hasItems(category0a, category1, category2a, category2b, operation1, operation2, operation3));
    }

    @Test
    public void testDeleteFavorite() throws Exception {
        assertNotNull(favoriteRepository.findOne(favorite1.getId()));
        this.mockMvc.perform(delete(ENDPOINT_URL + "/" + favorite1.getId())).andExpect(status().isNoContent());
        assertNull(favoriteRepository.findOne(favorite1.getId()));
    }

    @Test
    public void testDeleteFavoriteRecursivelyDeletesFavoriteChildren() throws Exception {
        FavoriteEntity entity = new FavoriteEntity();
        entity.setExternalUser(user1);
        entity.setCategory(category0a);
        entity = favoriteService.create(entity);
        List<FavoriteEntity> favorites = favoriteRepository.findByExternalUser(user1);
        assertEquals(7, favorites.size());
        this.mockMvc.perform(delete(ENDPOINT_URL + "/" + entity.getId())).andExpect(status().isNoContent());
        favorites = favoriteRepository.findByExternalUser(user1);
        assertEquals(0, favorites.size());
    }

    @Test
    public void testFavoriteCantHaveOperationAndCategoryAtTheSameTime() throws Exception {
        FavoriteDto dto = new FavoriteDto();
        dto.setExternalUser(externalUserAccountMapper.toDto(user1));
        dto.setOperation(operationMapper.toDto(operation1));
        dto.setCategory(categoryMapper.toDto(category0a));

        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", equalToIgnoringCase("exception.external_users.favorite.cannot_set_both_operation_and_category")));
    }

    @Test
    public void testUserCantHaveTheSameFavoriteTwice() throws Exception {
        FavoriteDto dto = new FavoriteDto();
        dto.setExternalUser(externalUserAccountMapper.toDto(user1));
        dto.setOperation(operationMapper.toDto(operation1));

        this.mockMvc.perform(post(ENDPOINT_URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetFavoriteByUser() throws Exception {
        this.mockMvc.perform(get(String.format("%s?query=USER_ID EQ '%s'", ENDPOINT_URL, user1.getId()))).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].operation.name.texts[0].label", is(operation1.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void sortFavoriteByCreatedDate() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "?sort=CREATED_DATE,desc")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(favorite2.getId().intValue()))).andExpect(jsonPath("$[1].id", is(favorite1.getId().intValue())));

        this.mockMvc.perform(get(ENDPOINT_URL + "?sort=CREATED_DATE,asc")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(favorite1.getId().intValue()))).andExpect(jsonPath("$[1].id", is(favorite2.getId().intValue())));
    }
}
