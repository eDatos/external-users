package es.gobcan.istac.edatos.external.users.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component
public class NoticesInternalHealthIndicator extends AbstractHealthIndicator {
    private final MetadataConfigurationService metadataConfigurationService;

    public NoticesInternalHealthIndicator(MetadataConfigurationService metadataConfigurationService) {
        this.metadataConfigurationService = metadataConfigurationService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        String endpoint = metadataConfigurationService.retrieveNoticesInternalApiUrlBase() + "?_wadl";
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject(endpoint, String.class);
            builder.withDetail("endpoint", endpoint).up();
        } catch (Exception e) {
            builder.withException(e).down();
        }
    }
}
