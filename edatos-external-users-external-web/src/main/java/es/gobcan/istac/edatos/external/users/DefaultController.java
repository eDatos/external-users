package es.gobcan.istac.edatos.external.users;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import es.gobcan.istac.edatos.external.users.web.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DefaultController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private MetadataConfigurationService metadataService;

    private final Logger log = LoggerFactory.getLogger(DefaultController.class);

    private String faviconUrl;

    @PostConstruct
    public void init() {
        // TODO EDATOS-3215 y EDATOS-3266 Pendiente de cambio : this.faviconUrl = this.metadataService.retrieveFaviconUrl();
        this.faviconUrl = metadataService.findProperty(applicationProperties.getMetadata().getMetamacFaviconUrlKey());
    }

    @RequestMapping(value = {"", "/index.html", "/**/{path:[^\\.]*}"})
    @SuppressWarnings("unchecked")
    public ModelAndView index(HttpServletRequest request) throws Exception {
        log.debug("DefaultController: Contextpath = {}  ServletPath = {}", request.getContextPath(), request.getServletPath());
        Map<String, Object> model = new HashMap<>();
        model.put("endpoint", applicationProperties.getEndpoint());
        model.put("faviconUrl", this.faviconUrl);
        model.put("headerHtml", renderHeaderHtml());

        Map<String, Object> flashMap = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            model.putAll(flashMap);
        }
        return new ModelAndView("index", model);
    }

    public String renderHeaderHtml() throws Exception {
        RestTemplate plantilla = new RestTemplate();
        String headerHtml = plantilla.getForObject(metadataService.retrieveAppStyleHeaderUrl(), String.class);
        return headerHtml;
    }

}
