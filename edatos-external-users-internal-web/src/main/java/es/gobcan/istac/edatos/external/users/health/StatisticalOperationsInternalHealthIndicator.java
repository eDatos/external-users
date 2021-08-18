package es.gobcan.istac.edatos.external.users.health;

import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.StatisticalOperationsRestInternalFacadeV10;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.service.EDatosApisLocator;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component
public class StatisticalOperationsInternalHealthIndicator extends AbstractHealthIndicator {
    private final EDatosApisLocator eDatosApisLocator;
    private final MetadataConfigurationService metadataConfigurationService;

    public StatisticalOperationsInternalHealthIndicator(EDatosApisLocator eDatosApisLocator, MetadataConfigurationService metadataConfigurationService) {
        this.eDatosApisLocator = eDatosApisLocator;
        this.metadataConfigurationService = metadataConfigurationService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        StatisticalOperationsRestInternalFacadeV10 operationsRestInternal = eDatosApisLocator.operationsInternal();
        try {
            operationsRestInternal.findOperations(null, null, "1", null);
            builder.withDetail("endpoint", metadataConfigurationService.retrieveStatisticalOperationsInternalApiUrlBase()).up();
        } catch (Exception e) {
            builder.withException(e).down();
        }
    }
}
