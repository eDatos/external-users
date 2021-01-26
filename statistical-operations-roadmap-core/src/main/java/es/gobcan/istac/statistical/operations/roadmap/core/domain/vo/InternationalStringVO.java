package es.gobcan.istac.statistical.operations.roadmap.core.domain.vo;

import java.util.HashSet;
import java.util.Set;

public class InternationalStringVO {

    private Set<LocalisedStringVO> texts = new HashSet<>();

    public InternationalStringVO() {
    }

    public Set<LocalisedStringVO> getTexts() {
        return texts;
    }

    public void addText(LocalisedStringVO textElement) {
        getTexts().add(textElement);
    }

    public void removeText(LocalisedStringVO textElement) {
        getTexts().remove(textElement);
    }

    public void removeAllTexts() {
        getTexts().clear();
    }

    public LocalisedStringVO getLocalisedLabelEntity(String locale) {
        if (locale == null) {
            return null;
        }
        for (LocalisedStringVO localstr : getTexts()) {
            if (locale.equalsIgnoreCase(localstr.getLocale())) {
                return localstr;
            }
        }
        return null;
    }

    public String getLocalisedLabel(String locale) {
        if (locale == null) {
            return null;
        }
        for (LocalisedStringVO localstr : getTexts()) {
            if (locale.equalsIgnoreCase(localstr.getLocale())) {
                return localstr.getLabel();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "InternationalStringVO [texts=" + texts + "]";
    }
}
