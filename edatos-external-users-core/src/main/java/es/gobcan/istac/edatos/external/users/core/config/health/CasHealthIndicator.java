package es.gobcan.istac.edatos.external.users.core.config.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;

@Component
public class CasHealthIndicator extends AbstractHealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(CasHealthIndicator.class);

    @Autowired
    private MetadataProperties metadataProperties;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        final String casEndPoint = metadataProperties.getMetamacCasPrefix();
        builder.withDetail("endpoint", casEndPoint);
        if (HttpStatus.ACCEPTED.equals(getUrlStatus(casEndPoint)) || HttpStatus.OK.equals(getUrlStatus(casEndPoint))) {
            builder.up();
        } else {
            logger.warn("Cas not available. " + "Impossible to reach");
            builder.down();
        }
    }

    public final HttpStatus getUrlStatus(String url) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(factory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = restTemplate.getForEntity(url, String.class);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return response.getStatusCode();
    }
}
