package es.gobcan.istac.statistical.operations.roadmap.external.api.invocation;

import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;

public interface CommonMetadataRestExternalFacade {

    public Configuration retrieveConfigurationById(String id);
}
