package es.gobcan.istac.edatos.external.users.external.api.invocation;

import java.util.List;

import org.siemac.edatos.core.common.util.shared.UrnUtils;
import org.siemac.edatos.rest.exception.RestException;
import org.siemac.edatos.rest.exception.util.RestExceptionUtils;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources.v1_0.domain.ItemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("srmRestExternalFacade")
public class SrmRestExternalFacadeImpl implements SrmRestExternalFacade {

    private final Logger logger = LoggerFactory.getLogger(SrmRestExternalFacadeImpl.class);

    @Autowired
    private ApisLocator restApiLocator;

    @Override
    public List<ItemResource> retrieveConceptsByConceptScheme(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            Concepts concepts = restApiLocator.getSrmRestExternalFacadeV10().findConcepts(agencyID, resourceID, version, null, null, null, null, null);
            return concepts.getConcepts();
        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    private RestException toRestException(Exception e) {
        logger.error("Error", e);
        return RestExceptionUtils.toRestException(e);
    }
}
