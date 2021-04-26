package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import org.junit.Before;
import org.junit.Ignore;
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
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
@Transactional
public class OperationResourceInternalTest {

    private static final String ENDPOINT_URL = OperationResource.BASE_URL;
    private static final String LOCALE = "es";

    @Autowired
    private OperationResource operationResource;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;

    private OperationEntity operation0;

    private OperationEntity operation1;

    private OperationEntity operation2;

    private CategoryEntity category0;

    private CategoryEntity category1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(operationResource).setControllerAdvice(exceptionTranslator).setCustomArgumentResolvers(pageableArgumentResolver).build();
    }

    @Before
    public void populateDatabase() {
        LocalisedStringVO locStr = new LocalisedStringVO();
        locStr.setLabel("Categoría abuela");
        locStr.setLocale(LOCALE);

        InternationalStringVO intStr = new InternationalStringVO();
        intStr.addText(locStr);

        category0 = new CategoryEntity();
        category0.setName(intStr);
        category0.setCode("001");
        categoryRepository.saveAndFlush(category0);

        // ------------------------------------------------------

        locStr = new LocalisedStringVO();
        locStr.setLabel("Categoría madre");
        locStr.setLocale(LOCALE);

        intStr = new InternationalStringVO();
        intStr.addText(locStr);

        category1 = new CategoryEntity();
        category1.setName(intStr);
        category1.setCode("002");
        category1.setParent(category0);
        categoryRepository.saveAndFlush(category1);

        // ------------------------------------------------------

        locStr = new LocalisedStringVO();
        locStr.setLabel("Operación 1");
        locStr.setLocale(LOCALE);

        intStr = new InternationalStringVO();
        intStr.addText(locStr);

        operation0 = new OperationEntity();
        operation0.setName(intStr);
        operation0.setCode("001");
        operation0.setCategory(category0);
        operationRepository.saveAndFlush(operation0);

        // ------------------------------------------------------

        locStr = new LocalisedStringVO();
        locStr.setLabel("Operación 2");
        locStr.setLocale(LOCALE);

        intStr = new InternationalStringVO();
        intStr.addText(locStr);

        operation1 = new OperationEntity();
        operation1.setName(intStr);
        operation1.setCode("002");
        operation1.setCategory(category1);
        operationRepository.saveAndFlush(operation1);

        // ------------------------------------------------------

        locStr = new LocalisedStringVO();
        locStr.setLabel("Operación 3");
        locStr.setLocale(LOCALE);

        intStr = new InternationalStringVO();
        intStr.addText(locStr);

        operation2 = new OperationEntity();
        operation2.setName(intStr);
        operation2.setCode("003");
        operation2.setCategory(category1);
        operationRepository.saveAndFlush(operation2);
    }

    @Test
    public void testGetOperationById() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL + "/" + operation0.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(operation0.getId().intValue())))
                .andExpect(jsonPath("$.name.texts[0].label", is(operation0.getName().getLocalisedLabel(LOCALE)))).andExpect(jsonPath("$.category.id", is(category0.getId().intValue())));
    }

    @Test
    public void testGetListOfOperations() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].name.texts[0].label", is(operation0.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$.[1].name.texts[0].label", is(operation1.getName().getLocalisedLabel(LOCALE))))
                .andExpect(jsonPath("$.[2].name.texts[0].label", is(operation2.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testGetOperationByCode() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL).param("query", "CODE EQ '" + operation2.getCode() + "'")).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name.texts[0].label", is(operation2.getName().getLocalisedLabel(LOCALE))));
    }

    @Test
    public void testGetOperationByCategory() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL).param("query", "CATEGORY_ID EQ " + category1.getId())).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(operation1.getId().intValue()))).andExpect(jsonPath("$[1].id", is(operation2.getId().intValue())));
    }

    // TODO(EDATOS-3294): This test isn't working because the criteria is not ready yet to work with jsonb fields.
    @Test
    @Ignore("TODO(EDATOS-3294): This test it's not working because the criteria it's not ready yet to work with jsonb fields.")
    public void testGetOperationByName() throws Exception {
        this.mockMvc.perform(get(ENDPOINT_URL).param("query", "NAME ILIKE '%3%'")).andDo(print()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(operation1.getName().getLocalisedLabel(LOCALE)))).andExpect(jsonPath("$[1].id", is(operation2.getName().getLocalisedLabel(LOCALE))));
    }
}
