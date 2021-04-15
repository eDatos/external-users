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
import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Language;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.Treatment;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.repository.FavoriteRepository;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.FavoriteMapper;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class FavoriteResourceInternalTest {

    private static final String ENDPOINT_URL = "/api/favorites";

    @Autowired
    private FavoriteResource favoriteResource;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExternalUserRepository externalUserRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;
    private ExternalUserEntity user1;
    private CategoryEntity category1;
    private FavoriteEntity favorite1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(favoriteResource)
                                      .setControllerAdvice(exceptionTranslator)
                                      .setCustomArgumentResolvers(pageableArgumentResolver)
                                      .build();
    }

    @Before
    public void populateDatabase() {
        user1 = new ExternalUserEntity();
        user1.setName("Montserrat");
        user1.setSurname1("Pons");
        user1.setSurname2("Ferrer");
        user1.setPassword("password1");
        user1.setEmail("user1@gmail.com");
        user1.setTreatment(Treatment.LADY);
        user1.setLanguage(Language.CATALAN);
        externalUserRepository.saveAndFlush(user1);

        LocalisedStringVO locStr = new LocalisedStringVO();
        locStr.setLabel("est marc");
        locStr.setLocale("es");

        InternationalStringVO intStr = new InternationalStringVO();
        intStr.addText(locStr);

        category1 = new CategoryEntity();
        category1.setName(intStr);
        category1.setUri("uri:000");
        category1.setUrn("urn:000");
        category1.setCode("000");
        categoryRepository.saveAndFlush(category1);

        favorite1 = new FavoriteEntity();
        favorite1.setExternalUser(user1);
        favorite1.setCategory(category1);
        favoriteRepository.saveAndFlush(favorite1);
    }

    @Test
    public void test() {
        FavoriteEntity fav = favoriteRepository.getOne(favorite1.getId());
        assertThat(fav.getId()).isNotNull();
        assertThat(fav.getCategory().getName().getLocalisedLabel("es")).isEqualTo("est marc");
    }
}
