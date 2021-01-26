package es.gobcan.istac.edatos.external.users.core.domain.vo;

public class LocalisedStringVO {

    String label;
    String locale;

    public LocalisedStringVO() {
    }

    public LocalisedStringVO(String label, String locale) {
        this.label = label;
        this.locale = locale;
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

    @Override
    public String toString() {
        return "LocalisedStringVO [label=" + label + ", locale=" + locale + "]";
    }
}
