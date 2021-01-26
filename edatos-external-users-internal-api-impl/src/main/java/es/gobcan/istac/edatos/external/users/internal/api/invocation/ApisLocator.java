package es.gobcan.istac.edatos.external.users.internal.api.invocation;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component
public class ApisLocator {

    @Autowired
    private MetadataConfigurationService configurationService;

    private CommonMetadataV1_0 commonMetadataRestExternalFacadeV10 = null;
    private SrmRestInternalFacadeV10 srmRestInternalFacadeV10 = null;

    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() throws EDatosException {
        if (commonMetadataRestExternalFacadeV10 == null) {
            String baseApi = configurationService.retrieveCommonMetadataExternalApiUrlBase();
            commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(baseApi, CommonMetadataV1_0.class, null, true);
        }
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");

        return commonMetadataRestExternalFacadeV10;
    }

    public SrmRestInternalFacadeV10 getSrmRestInternalFacadeV10() throws EDatosException {
        if (srmRestInternalFacadeV10 == null) {
            String baseApi = configurationService.retrieveSrmInternalApiUrlBase();
            srmRestInternalFacadeV10 = JAXRSClientFactory.create(baseApi, SrmRestInternalFacadeV10.class, null, true);
        }
        // reset thread context
        WebClient.client(srmRestInternalFacadeV10).reset();
        WebClient.client(srmRestInternalFacadeV10).accept("application/xml");

        return srmRestInternalFacadeV10;
    }
}
