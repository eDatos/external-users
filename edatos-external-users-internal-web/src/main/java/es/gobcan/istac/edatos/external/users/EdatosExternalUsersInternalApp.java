package es.gobcan.istac.edatos.external.users;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import es.gobcan.istac.edatos.external.users.core.config.Constants;
import es.gobcan.istac.edatos.external.users.core.config.DefaultProfileUtil;
import es.gobcan.istac.edatos.external.users.web.config.JHipsterExtraProperties;
import io.github.jhipster.config.JHipsterConstants;

@ComponentScan
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
@EnableConfigurationProperties({LiquibaseProperties.class, JHipsterExtraProperties.class})
public class EdatosExternalUsersInternalApp {

    private static final Logger log = LoggerFactory.getLogger(EdatosExternalUsersInternalApp.class);

    private final Environment env;

    public EdatosExternalUsersInternalApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes edatos_external_users.
     * <p>
     * Spring profiles can be configured with a program arguments
     * --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href=
     * "https://jhipster.github.io/profiles/">https://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_ENV)) {
            log.error("You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time.");
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(EdatosExternalUsersInternalApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info(
                "\n----------------------------------------------------------\n\t" + "La aplicaci??n '{}' se ha lanzado! URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t" + "Externa: \t{}://{}:{}\n\t"
                        + "Perfiles(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol, InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
                env.getActiveProfiles());
    }
}
