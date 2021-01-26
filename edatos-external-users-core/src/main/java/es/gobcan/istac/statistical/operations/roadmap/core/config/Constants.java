package es.gobcan.istac.statistical.operations.roadmap.core.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class Constants {

    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String SPRING_PROFILE_ENV = "env";

    public static final String SPRING_PROFILE_DEV = "dev";

    public static final String REMOVE_ORPHAN_FILES_CRON = "0 0 0 ? * SUN";

    public static final String NAME_ATTRIBUTE_LANG = "lang";

    public static final String DEFAULT_LANG = "es";

    public static final Locale DEFAULT_LOCALE = Locale.forLanguageTag(DEFAULT_LANG);

    public static final List<String> AVAILABLE_LANGS = Arrays.asList(Constants.DEFAULT_LANG, "en");

    private Constants() {
    }
}
