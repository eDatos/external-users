package es.gobcan.istac.statistical.operations.roadmap.external.api.invocation;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.srm.rest.external.v1_0.service.SrmRestExternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.roadmap.core.service.MetadataConfigurationService;

@Component
public class ApisLocator {

    @Autowired
    private MetadataConfigurationService configurationService;

    private CommonMetadataV1_0 commonMetadataRestExternalFacadeV10 = null;
    private SrmRestExternalFacadeV10 srmRestExternalFacadeV10 = null;

    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
        if (commonMetadataRestExternalFacadeV10 == null) {
            String baseApi = configurationService.retrieveCommonMetadataExternalApiUrlBase();
            commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(baseApi, CommonMetadataV1_0.class, null, true);
        }
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");

        return commonMetadataRestExternalFacadeV10;
    }

    public SrmRestExternalFacadeV10 getSrmRestExternalFacadeV10() {
        if (srmRestExternalFacadeV10 == null) {
            String baseApi = configurationService.retrieveSrmExternalApiUrlBase();
            srmRestExternalFacadeV10 = JAXRSClientFactory.create(baseApi, SrmRestExternalFacadeV10.class, null, true);
        }
        // reset thread context
        WebClient.client(srmRestExternalFacadeV10).reset();
        WebClient.client(srmRestExternalFacadeV10).accept("application/xml");

        return srmRestExternalFacadeV10;
    }
}
