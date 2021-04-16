package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class CategoryResourceInternalTest {

    private static final String ENDPOINT_URL = "/api/categories";
    private static final String LOCALE = "es";

    @Autowired
    private CategoryResource categoryResource;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;

    private CategoryEntity cat0;

    private CategoryEntity cat1;

    private CategoryEntity cat2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryResource).setControllerAdvice(exceptionTranslator).setCustomArgumentResolvers(pageableArgumentResolver).build();
    }

    @Before
    public void populateDatabase() {
        LocalisedStringVO locStr = new LocalisedStringVO();
        locStr.setLabel("Categoría abuela");
        locStr.setLocale(LOCALE);

        InternationalStringVO intStr = new InternationalStringVO();
        intStr.addText(locStr);

        cat0 = new CategoryEntity();
        cat0.setName(intStr);
        cat0.setUri("uri:001");
        cat0.setUrn("urn:001");
        cat0.setCode("001");
        categoryRepository.saveAndFlush(cat0);

        // ------------------------------------------------------

        locStr = new LocalisedStringVO();
        locStr.setLabel("Categoría madre");
        locStr.setLocale(LOCALE);

        intStr = new InternationalStringVO();
        intStr.addText(locStr);

        cat1 = new CategoryEntity();
        cat1.setName(intStr);
        cat1.setUri("uri:002");
        cat1.setUrn("urn:002");
        cat1.setCode("002");
        cat1.setParent(cat0);
        categoryRepository.saveAndFlush(cat1);

        // ------------------------------------------------------

        locStr = new LocalisedStringVO();
        locStr.setLabel("Categoría hija");
        locStr.setLocale(LOCALE);

        intStr = new InternationalStringVO();
        intStr.addText(locStr);

        cat2 = new CategoryEntity();
        cat2.setName(intStr);
        cat2.setUri("uri:003");
        cat2.setUrn("urn:003");
        cat2.setCode("003");
        cat2.setParent(cat1);
        categoryRepository.saveAndFlush(cat2);
    }

    @Test
    public void testGetCategoryById() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "/" + cat0.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(cat0.getId().intValue())))
                .andExpect(jsonPath("$.name.texts[0].label", is(cat0.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testCategoryReferencesItsChildren() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "/" + cat0.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(cat0.getId().intValue())))
                .andExpect(jsonPath("$.name.texts[0].label", is(cat0.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$.children[0].name.texts[0].label", is(cat1.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$.children[0].children[0].name.texts[0].label", is(cat2.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testGetListOfCategories() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].name.texts[0].label", is(cat0.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$.[1].name.texts[0].label", is(cat1.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$.[2].name.texts[0].label", is(cat2.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testGetTreeOfCategories() throws Exception {
        LocalisedStringVO locStr = new LocalisedStringVO();
        locStr.setLabel("Categoría abuela 2");
        locStr.setLocale(LOCALE);

        InternationalStringVO intStr = new InternationalStringVO();
        intStr.addText(locStr);

        CategoryEntity cat0b = new CategoryEntity();
        cat0b.setName(intStr);
        cat0b.setUri("uri:000b");
        cat0b.setUrn("urn:000b");
        cat0b.setCode("000b");
        categoryRepository.saveAndFlush(cat0b);

        this.mockMvc.perform(get(ENDPOINT_URL + "/tree")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name.texts[0].label", is(cat0.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$[0].children[0].name.texts[0].label", is(cat1.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$[0].children[0].children[0].name.texts[0].label", is(cat2.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$[1].name.texts[0].label", is(cat0b.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testGetCategoryByCode() throws Exception {
        this.mockMvc.perform(get(String.format("%s?query=CODE EQ '%s'", ENDPOINT_URL, cat2.getCode()))).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name.texts[0].label", is(cat2.getName().getLocalisedLabel(LOCALE))));
    }
}
