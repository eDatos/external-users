package es.gobcan.istac.edatos.external.users.core.service;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.metamac.notices.rest.internal.v1_0.service.NoticesV1_0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EDatosApisLocator {

    @Autowired
    private MetadataConfigurationService metadataConfigurationService;
    private NoticesV1_0 noticesV10 = null;

    public NoticesV1_0 getNoticesRestInternalFacadeV10() throws EDatosException {
        if (noticesV10 == null) {
            String baseApi = metadataConfigurationService.retrieveNoticesInternalApiUrlBase();
            noticesV10 = JAXRSClientFactory.create(baseApi, NoticesV1_0.class, null, true); // true to do thread safe
        }
        // reset thread context
        WebClient.client(noticesV10).reset();
        WebClient.client(noticesV10).accept("application/xml");

        return noticesV10;
    }
}
