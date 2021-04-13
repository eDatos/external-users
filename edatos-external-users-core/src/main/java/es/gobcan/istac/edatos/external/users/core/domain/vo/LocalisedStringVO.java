package es.gobcan.istac.edatos.external.users.core.domain.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LocalisedStringVO implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof LocalisedStringVO)) {
            return false;
        }

        LocalisedStringVO that = (LocalisedStringVO) o;

        return new EqualsBuilder().append(label, that.label).append(locale, that.locale).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(label).append(locale).toHashCode();
    }

    @Override
    public String toString() {
        return "LocalisedStringVO [label=" + label + ", locale=" + locale + "]";
    }
}
