package es.gobcan.istac.edatos.external.users.web.config;

import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.core.errors.AccessDeniedCustomHandler;
import es.gobcan.istac.edatos.external.users.web.security.provider.LoginPasswordAuthenticationProvider;
import es.gobcan.istac.edatos.external.users.web.security.filter.CaptchaFilter;
import es.gobcan.istac.edatos.external.users.web.security.filter.JWTAuthenticationFilter;
import es.gobcan.istac.edatos.external.users.web.security.filter.JWTAuthorizationFilter;
import es.gobcan.istac.edatos.external.users.web.security.provider.TokenProvider;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.Http401UnauthorizedEntryPoint;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private JHipsterProperties jHipsterProperties;

    private ApplicationProperties applicationProperties;
    
    @Autowired
    private MetadataProperties metadataProperties;

    private final Environment env;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, JHipsterProperties jHipsterProperties,
            ApplicationProperties applicationProperties, MetadataProperties metadataProperties, Environment env) {

        this.tokenProvider = tokenProvider;
        this.jHipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
        this.env = env;
    }

    @Bean
    public SecurityContextLogoutHandler logoutHandler() {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.setClearAuthentication(true);
        logoutHandler.setInvalidateHttpSession(true);
        return logoutHandler;
    }

    @Bean
    public Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint() {
        return new Http401UnauthorizedEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new LoginPasswordAuthenticationProvider();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    private CsrfTokenRepository getCsrfTokenRepository() {
        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        tokenRepository.setCookiePath("/");
        return tokenRepository;
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
        //@formatter:off
        http
            .addFilterBefore(new CaptchaFilter(metadataProperties), JWTAuthenticationFilter.class)
            .addFilter(new JWTAuthenticationFilter(authenticationProvider(), tokenProvider, mapper(),jHipsterProperties, applicationProperties, env))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), tokenProvider))
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler())
            .authenticationEntryPoint(http401UnauthorizedEntryPoint())
        .and()
            .csrf().csrfTokenRepository(this.getCsrfTokenRepository())
        .and()
            .headers().frameOptions().sameOrigin()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
                .antMatchers("/api/activate").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/profile-info").permitAll()
                .antMatchers("/api/account/**").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/recover-password/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/apis/operations-internal/**").permitAll()
                .antMatchers("/api/data-protection-policy").permitAll()
                .antMatchers("/api/captcha/**").permitAll()
                .antMatchers("/**").authenticated();
        //@formatter:on
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedCustomHandler();
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
