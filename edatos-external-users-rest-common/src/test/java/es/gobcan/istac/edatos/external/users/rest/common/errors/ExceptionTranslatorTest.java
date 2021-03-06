package es.gobcan.istac.edatos.external.users.rest.common.errors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.gobcan.istac.edatos.external.users.EdatosExternalUsersRestTestApp;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ExceptionTranslator;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the ExceptionTranslator controller advice.
 *
 * @see ExceptionTranslator
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdatosExternalUsersRestTestApp.class)
public class ExceptionTranslatorTest {

    @Autowired
    private ExceptionTranslatorTestController controller;

    @Autowired
    private ExceptionTranslator               exceptionTranslator;

    private MockMvc                           mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(exceptionTranslator).build();
    }

    @Test
    public void testConcurrencyFailure() throws Exception {
        mockMvc.perform(get("/test/concurrency-failure")).andExpect(status().isConflict()).andExpect(jsonPath("$.message").value(ErrorConstants.ERR_CONCURRENCY_FAILURE));
    }

    @Test
    public void testMethodArgumentNotValid() throws Exception {
        mockMvc.perform(post("/test/method-argument").content("{}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorConstants.ERR_VALIDATION)).andExpect(jsonPath("$.fieldErrors.[0].objectName").value("testDto"))
                .andExpect(jsonPath("$.fieldErrors.[0].field").value("test")).andExpect(jsonPath("$.fieldErrors.[0].message").value("NotNull"));
    }

    @Test
    public void testParameterizedError() throws Exception {
        mockMvc.perform(get("/test/parameterized-error")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("test parameterized error"))
                .andExpect(jsonPath("$.code").value("error.test")).andExpect(jsonPath("$.params").value(hasItems("param0_value", "param1_value")));
    }

    @Test
    public void testParameterizedErrorWithErrorList() throws Exception {
        mockMvc.perform(get("/test/parameterized-error2")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("test parameterized error"))
                .andExpect(jsonPath("$.code").value("error.test")).andExpect(jsonPath("$.params").value(hasItems("param0_value", "param1_value"))).andExpect(jsonPath("$.errorItems").isNotEmpty())
                .andExpect(jsonPath("$.errorItems.[*].message").value(hasItems("message1", "message2", "message3")))
                .andExpect(jsonPath("$.errorItems.[*].code").value(hasItems("code1", "code2", "code3"))).andExpect(jsonPath("$.errorItems[0].params").isEmpty())
                .andExpect(jsonPath("$.errorItems[1].params").value(hasItem("param_code2"))).andExpect(jsonPath("$.errorItems[2].params").value(hasItems("param1_code3", "param2_code3")));
    }

    @Test
    public void testExceptionWithResponseStatus() throws Exception {
        mockMvc.perform(get("/test/response-status")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("error.400"))
                .andExpect(jsonPath("$.description").value("test response status"));
    }

    @Test
    public void testInternalServerError() throws Exception {
        mockMvc.perform(get("/test/internal-server-error")).andExpect(status().isInternalServerError()).andExpect(jsonPath("$.message").value(ErrorConstants.ERR_INTERNAL_SERVER_ERROR))
                .andExpect(jsonPath("$.description").value("Internal server error"));
    }
}
