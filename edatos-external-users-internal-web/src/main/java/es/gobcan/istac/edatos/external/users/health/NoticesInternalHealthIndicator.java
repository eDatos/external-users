package es.gobcan.istac.edatos.external.users.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

import static es.gobcan.istac.edatos.external.users.core.config.Constants.HEALTH_INDICATOR_REQUEST_TIMEOUT_MS;

@Component
public class NoticesInternalHealthIndicator extends AbstractHealthIndicator {
    private final MetadataConfigurationService metadataConfigurationService;

    public NoticesInternalHealthIndicator(MetadataConfigurationService metadataConfigurationService) {
        this.metadataConfigurationService = metadataConfigurationService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        String endpoint = metadataConfigurationService.retrieveNoticesInternalApiUrlBase() + "?_wadl";
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(HEALTH_INDICATOR_REQUEST_TIMEOUT_MS);
        factory.setReadTimeout(HEALTH_INDICATOR_REQUEST_TIMEOUT_MS);
        RestTemplate restTemplate = new RestTemplate(factory);
        try {
            restTemplate.getForObject(endpoint, String.class);
            builder.withDetail("endpoint", endpoint).up();
        } catch (Exception e) {
            builder.withException(e).down();
        }
    }
}
