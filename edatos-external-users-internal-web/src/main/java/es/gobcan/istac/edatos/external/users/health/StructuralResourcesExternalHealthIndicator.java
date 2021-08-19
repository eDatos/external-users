package es.gobcan.istac.edatos.external.users.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Component
public class StructuralResourcesExternalHealthIndicator extends AbstractHealthIndicator {
    private final MetadataConfigurationService metadataConfigurationService;

    public StructuralResourcesExternalHealthIndicator(MetadataConfigurationService metadataConfigurationService) {
        this.metadataConfigurationService = metadataConfigurationService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        String endpoint = metadataConfigurationService.retrieveSrmExternalApiUrlBase() + "?_wadl";
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(factory);
        try {
            restTemplate.getForObject(endpoint, String.class);
            builder.withDetail("endpoint", endpoint).up();
        } catch (Exception e) {
            builder.withException(e).down();
        }
    }
}
