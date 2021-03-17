package es.gobcan.istac.edatos.external.users.web.config;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.Ehcache;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.web.security.jwt.JWTAuthenticationSuccessHandler;
import es.gobcan.istac.edatos.external.users.web.security.jwt.JWTFilter;
import es.gobcan.istac.edatos.external.users.web.security.jwt.TokenProvider;
import es.gobcan.istac.edatos.external.users.web.security.CasUserDetailsService;
import es.gobcan.istac.edatos.external.users.web.security.jwt.CasEhCacheBasedTicketCache;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.Http401UnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private JHipsterProperties jHipsterProperties;

    private ApplicationProperties applicationProperties;

    private MetadataProperties metadataProperties;

    private final Environment env;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, CorsFilter corsFilter, JHipsterProperties jHipsterProperties,
            ApplicationProperties applicationProperties, MetadataProperties metadataProperties, Environment env) {

        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jHipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
        this.metadataProperties = metadataProperties;
        this.env = env;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder.authenticationProvider(casAuthenticationProvider());
        } catch (Exception e) {
            throw new BeanInitializationException("Configuración de seguridad fallida", e);
        }
    }

    // ******************* CAS **********

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(StringUtils.removeEnd(applicationProperties.getCas().getService(), "/"));
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    // @Bean
    public CasEhCacheBasedTicketCache statelessTicketCache() {
        CasEhCacheBasedTicketCache statelessTicketCache = new CasEhCacheBasedTicketCache();
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("casTickets", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, CasAuthenticationToken.class, ResourcePoolsBuilder.heap(10))).build();
        cacheManager.init();

        Cache<String, CasAuthenticationToken> cache = cacheManager.getCache("casTickets", String.class, CasAuthenticationToken.class);
        statelessTicketCache.setCache((Ehcache<String, CasAuthenticationToken>) cache);

        return statelessTicketCache;
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(authenticationUserDetailsService());
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(casServiceTicketValidator());
        casAuthenticationProvider.setKey("EDATOS_EXTERNAL_USERS_CAS");
        return casAuthenticationProvider;
    }

    @Bean
    public CasUserDetailsService authenticationUserDetailsService() {
        return new CasUserDetailsService();
    }

    @Bean
    public Cas30ServiceTicketValidator casServiceTicketValidator() {
        return new Cas30ServiceTicketValidator(metadataProperties.getMetamacCasPrefix());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JWTAuthenticationSuccessHandler(tokenProvider, jHipsterProperties, applicationProperties, env);
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return casAuthenticationFilter;
    }

    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(metadataProperties.getMetamacCasPrefix());
        return singleSignOutFilter;
    }

    @Bean
    public SecurityContextLogoutHandler casLogoutHandler() {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.setClearAuthentication(true);
        logoutHandler.setInvalidateHttpSession(true);
        return logoutHandler;
    }

    @Bean
    public LogoutFilter requestCasGlobalLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(metadataProperties.getMetamacCasLogoutUrl(), casLogoutHandler());
        logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
        return logoutFilter;
    }

    @Bean
    public Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint() {
        return new Http401UnauthorizedEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //@formatter:off
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/*")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**")
            .antMatchers("/test/**");
        //@formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTFilter customFilter = new JWTFilter(tokenProvider);
        //@formatter:off
        http
        	.addFilter(casAuthenticationFilter())
            .addFilterBefore(corsFilter, CasAuthenticationFilter.class)
            .addFilterBefore(customFilter, CasAuthenticationFilter.class)
            .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class)
	    	.addFilterBefore(requestCasGlobalLogoutFilter(), LogoutFilter.class)
            .exceptionHandling()
        	.authenticationEntryPoint(http401UnauthorizedEntryPoint())
        .and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()

            .headers()
            .frameOptions().sameOrigin()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/profile-info").permitAll()
            .antMatchers("/management/metrics").access("@secChecker.canAccessMetrics(authentication)")
            .antMatchers("/management/health").access("@secChecker.canAccessHealth(authentication)")
            .antMatchers("/management/env").access("@secChecker.canAccessConfig(authentication)")
            .antMatchers("/management/configprops").access("@secChecker.canAccessConfig(authentication)")
            .antMatchers("/v2/api-docs/**").permitAll()
            .antMatchers("/apis/operations-internal/**").permitAll()
            .antMatchers("/**").authenticated();
        //@formatter:on
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
