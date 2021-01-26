package es.gobcan.istac.statistical.operations.roadmap.external.api.invocation;

import java.util.List;

import org.siemac.metamac.rest.structural_resources.v1_0.domain.ItemResource;

public interface SrmRestExternalFacade {

    public List<ItemResource> retrieveConceptsByConceptScheme(String urn);
}
