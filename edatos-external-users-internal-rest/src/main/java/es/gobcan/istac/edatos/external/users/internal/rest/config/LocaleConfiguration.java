package es.gobcan.istac.edatos.external.users.internal.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import es.gobcan.istac.edatos.external.users.core.config.Constants;

@Configuration
public class LocaleConfiguration extends WebMvcConfigurerAdapter {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        CookieLocaleResolver r = new CookieLocaleResolver();
        r.setDefaultLocale(Constants.DEFAULT_LOCALE);
        r.setCookieName(Constants.NAME_ATTRIBUTE_LANG);
        r.setCookieMaxAge(86400); // 24 * 60 * 60
        r.setCookieHttpOnly(false);
        return r;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(Constants.NAME_ATTRIBUTE_LANG);
        registry.addInterceptor(localeChangeInterceptor);
    }
}
