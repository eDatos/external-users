
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class MetamacHeaderTest {

    @Ignore
    @Test
    public void testRenderHeaderHtml() {
        RestTemplate plantilla = new RestTemplate();
        String headerHtml = plantilla.getForObject("https://estadisticas.arte-consultores.com/apps/organisations/istac/apis/header/header.html", String.class);

        assertThat(headerHtml, containsString("istacNavbarId"));
    }
}
