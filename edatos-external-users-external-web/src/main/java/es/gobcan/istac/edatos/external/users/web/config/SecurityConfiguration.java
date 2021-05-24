package es.gobcan.istac.edatos.external.users.web.config;

import es.gobcan.istac.edatos.external.users.core.config.MetadataProperties;
import es.gobcan.istac.edatos.external.users.web.security.provider.LoginPasswordAuthenticationProvider;
import es.gobcan.istac.edatos.external.users.web.security.filter.JWTAuthenticationFilter;
import es.gobcan.istac.edatos.external.users.web.security.filter.JWTAuthorizationFilter;
import es.gobcan.istac.edatos.external.users.web.security.provider.TokenProvider;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.Http401UnauthorizedEntryPoint;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new LoginPasswordAuthenticationProvider();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
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
        // .addFilterBefore(requestGlobalLogoutFilter(), LogoutFilter.class) // TODO EDATOS-3287 Still to be set up
        http
            .addFilter(new JWTAuthenticationFilter(authenticationProvider(), tokenProvider, mapper(),jHipsterProperties, applicationProperties, env))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), tokenProvider))
            .exceptionHandling()
            .authenticationEntryPoint(http401UnauthorizedEntryPoint())
        .and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers("/api/login")
            .ignoringAntMatchers("/api/filters")
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
                .antMatchers("/**").authenticated();
        //@formatter:on
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
