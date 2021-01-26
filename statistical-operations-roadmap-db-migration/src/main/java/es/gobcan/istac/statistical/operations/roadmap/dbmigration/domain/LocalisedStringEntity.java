package es.gobcan.istac.statistical.operations.roadmap.dbmigration.domain;

public class LocalisedStringEntity {

    public final class Columns {

        public static final String ID = "ID";
        public static final String LABEL = "LABEL";
        public static final String LOCALE = "LOCALE";
        public static final String IS_UNMODIFIABLE = "IS_UNMODIFIABLE";
        public static final String VERSION = "VERSION";
        public static final String INTERNATIONAL_STRING_FK = "INTERNATIONAL_STRING_FK";

        private Columns() {
        }
    }

    private Long id;
    private String label;
    private String locale;
    private Boolean isUnmodifiable;
    private Long version;
    private Long internationalStringFk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Boolean getIsUnmodifiable() {
        return isUnmodifiable;
    }

    public void setIsUnmodifiable(Boolean isUnmodifiable) {
        this.isUnmodifiable = isUnmodifiable;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getInternationalStringFk() {
        return internationalStringFk;
    }

    public void setInternationalStringFk(Long internationalStringFk) {
        this.internationalStringFk = internationalStringFk;
    }
}
