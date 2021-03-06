package es.gobcan.istac.edatos.external.users.web.config;

import java.io.File;
import java.nio.file.Paths;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.siemac.edatos.rest.common.filter.CORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;

import es.gobcan.istac.edatos.external.users.web.security.filter.CustomStaticsResourceHttpHeadersFilter;
import es.gobcan.istac.edatos.external.users.ApiLatestURLRewriteFilter;
import es.gobcan.istac.edatos.external.users.core.config.Constants;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.web.filter.CachingHttpHeadersFilter;
import io.undertow.UndertowOptions;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    private MetricRegistry metricRegistry;

    public WebConfigurer(Environment env, JHipsterProperties jHipsterProperties) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Configuraci??n de aplicaci??n usando perfiles: {}", (Object[]) env.getActiveProfiles());
        }
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        initMetrics(servletContext, disps);
        initApiLastestUrlRewriteFilter(servletContext, disps);
        initApiCORSFilter(servletContext, disps);
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_ENV)) {
            initCachingHttpHeadersFilter(servletContext, disps);
            initCustomStaticResourceHttpHeadersFilter(servletContext, disps);
        }
        log.info("Aplicaci??n web configurada");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
        mappings.add("html", "text/html;charset=utf-8");
        // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
        mappings.add("json", "text/html;charset=utf-8");
        container.setMimeMappings(mappings);
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the
        // static web assets.
        setLocationForStaticAssets(container);

        /*
         * Enable HTTP/2 for Undertow -
         * https://twitter.com/ankinson/status/829256167700492288 HTTP/2 requires HTTPS,
         * so HTTP requests will fallback to HTTP/1.1. See the JHipsterProperties class
         * and your application-*.yml configuration files for more information.
         */
        if (jHipsterProperties.getHttp().getVersion().equals(JHipsterProperties.Http.Version.V_2_0) && container instanceof UndertowEmbeddedServletContainerFactory) {

            ((UndertowEmbeddedServletContainerFactory) container).addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
        }
    }

    private void setLocationForStaticAssets(ConfigurableEmbeddedServletContainer container) {
        File root;
        String prefixPath = resolvePathPrefix();
        root = new File(prefixPath + "target/www/");
        if (root.exists() && root.isDirectory()) {
            container.setDocumentRoot(root);
        }
    }

    /**
     * Resolve path prefix to static resources.
     */
    private String resolvePathPrefix() {
        String fullExecutablePath = this.getClass().getResource("").getPath();
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");
        int extractionEndIndex = extractedPath.indexOf("target/");
        if (extractionEndIndex <= 0) {
            return "";
        }
        return extractedPath.substring(0, extractionEndIndex);
    }

    /**
     * Initializes the caching HTTP Headers Filter.
     */
    private void initCachingHttpHeadersFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Registering Caching HTTP Headers Filter");
        FilterRegistration.Dynamic cachingHttpHeadersFilter = servletContext.addFilter("cachingHttpHeadersFilter", new CachingHttpHeadersFilter(jHipsterProperties));

        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/assets/*");
        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*");
        cachingHttpHeadersFilter.setAsyncSupported(true);
    }

    private void initCustomStaticResourceHttpHeadersFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Registering Custom static resources HTTP Headers Filter");
        FilterRegistration.Dynamic customStaticResourcesHttpHeadersFilter = servletContext.addFilter("customStaticResourcesHttpHeadersFilter", new CustomStaticsResourceHttpHeadersFilter());

        customStaticResourcesHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/assets/*");
        customStaticResourcesHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*");
        customStaticResourcesHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/i18n/*");
        customStaticResourcesHttpHeadersFilter.setAsyncSupported(true);
    }

    private void initApiLastestUrlRewriteFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Registering API Lastest URL Rewrite Filter");
        FilterRegistration.Dynamic filter = servletContext.addFilter("apiLatestURLRewriteFilter", new ApiLatestURLRewriteFilter());
        filter.addMappingForUrlPatterns(disps, true, "/apis/*");
    }

    private void initApiCORSFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Registering API CORS Filter");
        FilterRegistration.Dynamic filter = servletContext.addFilter("apiCORSFilter", new CORSFilter());
        filter.addMappingForUrlPatterns(disps, true, "/apis/*");
    }

    /**
     * Initializes Metrics.
     */
    private void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Inicializando regitros de Metrics");
        servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, metricRegistry);
        servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry);

        log.debug("Registrando filtros de Metrics");
        FilterRegistration.Dynamic metricsFilter = servletContext.addFilter("webappMetricsFilter", new InstrumentedFilter());

        metricsFilter.addMappingForUrlPatterns(disps, true, "/*");
        metricsFilter.setAsyncSupported(true);

        log.debug("Registrando Metrics Servlet");
        ServletRegistration.Dynamic metricsAdminServlet = servletContext.addServlet("metricsServlet", new MetricsServlet());

        metricsAdminServlet.addMapping("/management/metrics/*");
        metricsAdminServlet.setAsyncSupported(true);
        metricsAdminServlet.setLoadOnStartup(2);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = jHipsterProperties.getCors();
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            log.debug("Registrando filtro CORS ");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        return new CorsFilter(source);
    }

    @Autowired(required = false)
    public void setMetricRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }
}
