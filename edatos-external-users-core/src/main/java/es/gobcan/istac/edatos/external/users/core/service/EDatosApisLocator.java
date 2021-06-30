package es.gobcan.istac.edatos.external.users.core.service;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.metamac.notices.rest.internal.v1_0.service.NoticesV1_0;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.srm.rest.external.v1_0.service.SrmRestExternalFacadeV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;

@Service
public class EDatosApisLocator {

    private final Logger log = LoggerFactory.getLogger(EDatosApisLocator.class);
    private final MetadataConfigurationService metadataConfigurationService;

    private NoticesV1_0 noticesV10;
    private SrmRestExternalFacadeV10 srmV10;

    public EDatosApisLocator(MetadataConfigurationService metadataConfigurationService) {
        this.metadataConfigurationService = metadataConfigurationService;
    }

    public NoticesV1_0 getNoticesRestInternalFacadeV10() throws EDatosException {
        if (noticesV10 == null) {
            String baseApi = metadataConfigurationService.retrieveNoticesInternalApiUrlBase();
            noticesV10 = JAXRSClientFactory.create(baseApi, NoticesV1_0.class, null, true);
        }
        WebClient.client(noticesV10).reset();
        WebClient.client(noticesV10).accept("application/xml");

        return noticesV10;
    }

    public SrmRestExternalFacadeV10 srmExternal() {
        if (srmV10 == null) {
            String baseApiUrl = metadataConfigurationService.retrieveSrmExternalApiUrlBase();
            log.info("Setting SRM external API base URL to '{}'", baseApiUrl);
            srmV10 = JAXRSClientFactory.create(baseApiUrl, SrmRestExternalFacadeV10.class, null, true);
        }
        WebClient.client(srmV10).reset();
        WebClient.client(srmV10).accept("application/xml");
        return srmV10;
    }
}
