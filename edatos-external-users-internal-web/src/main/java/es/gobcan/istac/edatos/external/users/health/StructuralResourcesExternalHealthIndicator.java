package es.gobcan.istac.edatos.external.users.health;

import org.siemac.metamac.srm.rest.external.v1_0.service.SrmRestExternalFacadeV10;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.service.EDatosApisLocator;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component
public class StructuralResourcesExternalHealthIndicator extends AbstractHealthIndicator {
    private final EDatosApisLocator eDatosApisLocator;
    private final MetadataConfigurationService metadataConfigurationService;

    public StructuralResourcesExternalHealthIndicator(EDatosApisLocator eDatosApisLocator, MetadataConfigurationService metadataConfigurationService) {
        this.eDatosApisLocator = eDatosApisLocator;
        this.metadataConfigurationService = metadataConfigurationService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        SrmRestExternalFacadeV10 srmRestExternal = eDatosApisLocator.srmExternal();
        try {
            srmRestExternal.findCategories("~all", "~all", "~all", null, null, "1", null);
            builder.withDetail("endpoint", metadataConfigurationService.retrieveSrmExternalApiUrlBase()).up();
        } catch (Exception e) {
            builder.withException(e).down();
        }
    }
}
