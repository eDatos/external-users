package es.gobcan.istac.edatos.external.users.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
public class ApplicationProperties {

    private final Metadata metadata = new Metadata();
    private final Cas cas = new Cas();
    private final Endpoint endpoint = new Endpoint();

    public Metadata getMetadata() {
        return metadata;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public static class Metadata {

        private String endpoint;
        private String metamacNavbarKey;
        private String metamacCasPrefixKey;
        private String metamacCasLoginUrlKey;
        private String metamacCasLogoutUrlKey;
        private String metamacFaviconUrlKey;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getMetamacNavbarKey() {
            return metamacNavbarKey;
        }

        public void setMetamacNavbarKey(String metamacNavbarKey) {
            this.metamacNavbarKey = metamacNavbarKey;
        }

        public String getMetamacCasPrefixKey() {
            return metamacCasPrefixKey;
        }

        public void setMetamacCasPrefixKey(String metamacCasPrefixKey) {
            this.metamacCasPrefixKey = metamacCasPrefixKey;
        }

        public String getMetamacCasLoginUrlKey() {
            return metamacCasLoginUrlKey;
        }

        public void setMetamacCasLoginUrlKey(String metamacCasLoginUrlKey) {
            this.metamacCasLoginUrlKey = metamacCasLoginUrlKey;
        }

        public String getMetamacCasLogoutUrlKey() {
            return metamacCasLogoutUrlKey;
        }

        public void setMetamacCasLogoutUrlKey(String metamacCasLogoutUrlKey) {
            this.metamacCasLogoutUrlKey = metamacCasLogoutUrlKey;
        }

        public String getMetamacFaviconUrlKey() {
            return metamacFaviconUrlKey;
        }

        public void setMetamacFaviconUrlKey(String metamacFaviconUrlKey) {
            this.metamacFaviconUrlKey = metamacFaviconUrlKey;
        }
    }

    public static class Endpoint {

        // Required
        private String appUrl;

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }
    }

    public Cas getCas() {
        return cas;
    }

    public static class Cas {

        // Required
        private String service;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

    }

}
