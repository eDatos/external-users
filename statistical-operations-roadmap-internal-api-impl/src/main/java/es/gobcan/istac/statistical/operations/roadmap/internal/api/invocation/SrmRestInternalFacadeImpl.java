package es.gobcan.istac.statistical.operations.roadmap.internal.api.invocation;

import java.util.List;

import org.siemac.edatos.core.common.util.shared.UrnUtils;
import org.siemac.edatos.rest.exception.RestException;
import org.siemac.edatos.rest.exception.util.RestExceptionUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("srmRestInternalFacade")
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    private final Logger logger = LoggerFactory.getLogger(SrmRestInternalFacadeImpl.class);

    @Autowired
    private ApisLocator restApiLocator;

    @Override
    public List<ItemResourceInternal> retrieveConceptsByConceptScheme(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            Concepts concepts = restApiLocator.getSrmRestInternalFacadeV10().findConcepts(agencyID, resourceID, version, null, null, null, null, null);
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
