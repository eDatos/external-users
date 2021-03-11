package es.gobcan.istac.edatos.external.users.web.config;

import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.web.security.filter.UsernamePasswordAuthenticationFilter;
import es.gobcan.istac.edatos.external.users.web.security.jwt.JWTAuthenticationSuccessHandler;
import es.gobcan.istac.edatos.external.users.web.security.jwt.JWTFilter;
import es.gobcan.istac.edatos.external.users.web.security.jwt.TokenProvider;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.Http401UnauthorizedEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
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

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private JHipsterProperties jHipsterProperties;

    private ApplicationProperties applicationProperties;

    private MetadataProperties metadataProperties;

    private final Environment env;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, JHipsterProperties jHipsterProperties,
            ApplicationProperties applicationProperties, MetadataProperties metadataProperties, Environment env) {

        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.jHipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
        this.metadataProperties = metadataProperties;
        this.env = env;
    }

    /*
     * @PostConstruct
     * public void init() { // TODO EDATOS-3287 Still to be set up
     * try {
     * authenticationManagerBuilder.authenticationProvider(casAuthenticationProvider());
     * } catch (Exception e) {
     * throw new BeanInitializationException("Configuración de seguridad fallida", e);
     * }
     * }
     */

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JWTAuthenticationSuccessHandler(tokenProvider, jHipsterProperties, applicationProperties, env);
    }

    @Bean
    public SecurityContextLogoutHandler logoutHandler() {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.setClearAuthentication(true);
        logoutHandler.setInvalidateHttpSession(true);
        return logoutHandler;
    }

    @Bean
    public LogoutFilter requestGlobalLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(metadataProperties.getMetamacCasLogoutUrl(), logoutHandler());
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
        // JWTFilter customFilter = new JWTFilter(tokenProvider); // TODO EDATOS-3287 Still to be set up
        // UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter(tokenProvider, jHipsterProperties);
        // .addFilter(usernamePasswordAuthenticationFilter)
        // .addFilterBefore(requestGlobalLogoutFilter(), LogoutFilter.class)
        http.exceptionHandling().authenticationEntryPoint(http401UnauthorizedEntryPoint()).and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().headers()
                .frameOptions().sameOrigin().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers("/api/activate").permitAll()
                .antMatchers("/api/authenticate").permitAll().antMatchers("/api/profile-info").permitAll().antMatchers("/account").permitAll().antMatchers("/login").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll().antMatchers("/apis/operations-internal/**").permitAll().antMatchers("/management/metrics")
                .access("@secChecker.puedeConsultarMetrica(authentication)").antMatchers("/management/health").access("@secChecker.puedeConsultarSalud(authentication)").antMatchers("/management/env")
                .access("@secChecker.puedeConsultarConfig(authentication)").antMatchers("/management/configprops").access("@secChecker.puedeConsultarConfig(authentication)").antMatchers("/**")
                .authenticated();;
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
