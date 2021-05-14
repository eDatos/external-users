package es.gobcan.istac.edatos.external.users.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
public class ApplicationProperties {

    private final Metadata metadata = new Metadata();
    private final Endpoint endpoint = new Endpoint();
    private final Metamac metamac = new Metamac();

    public Metadata getMetadata() {
        return metadata;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public Metamac getMetamac() {
        return metamac;
    }

    public static class Metadata {

        private String endpoint;
        private String metamacNavbarKey;
        private String metamacFaviconUrlKey;
        private String metamacNavbarPathKey;

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

        public String getMetamacFaviconUrlKey() {
            return metamacFaviconUrlKey;
        }

        public void setMetamacFaviconUrlKey(String metamacFaviconUrlKey) {
            this.metamacFaviconUrlKey = metamacFaviconUrlKey;
        }

        public String getMetamacNavbarPathKey() {
            return metamacNavbarPathKey;
        }

        public void setMetamacNavbarPathKey(String metamacNavbarPathKey) {
            this.metamacNavbarPathKey = metamacNavbarPathKey;
        }

    }

    public static class Metamac {

        private static final String PERMALINK_PATH = "https://estadisticas.arte-consultores.com/istac/visualizer/data.html?permalink=";

        private String metamacPortalVisualizer;

        // TODO-3338 it is pending to add the property in the database
        public String getMetamacPortalVisualizer() {
            return PERMALINK_PATH;
        }

        public void setMetamacPortalVisualizer(String metamacPortalVisualizer) {
            this.metamacPortalVisualizer = metamacPortalVisualizer;
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

}
