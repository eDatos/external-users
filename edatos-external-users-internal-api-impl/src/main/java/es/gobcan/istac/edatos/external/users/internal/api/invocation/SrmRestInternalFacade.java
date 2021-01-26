package es.gobcan.istac.edatos.external.users.internal.api.invocation;

import java.util.List;

public interface SrmRestInternalFacade {

    public List<org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal> retrieveConceptsByConceptScheme(String urn);
}
