package es.gobcan.istac.edatos.external.users.core.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_international_strings")
public class InternationalStringEntity implements Serializable {

    private static final long serialVersionUID = 4105632098240476963L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_international_strings")
    @SequenceGenerator(name = "seq_tb_international_strings", sequenceName = "seq_tb_international_strings", allocationSize = 50, initialValue = 1)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internationalString", fetch = FetchType.EAGER, orphanRemoval = true)
    @NotNull
    private Set<LocalisedStringEntity> texts = new HashSet<>();

    /**
     * Added explicitly to avoid that Sculptor generate UUID
     */
    public Long getId() {
        return id;
    }

    /**
     * The id is not intended to be changed or assigned manually, but
     * for test purpose it is allowed to assign the id.
     */
    protected void setId(Long id) {
        if ((this.id != null) && !this.id.equals(id)) {
            throw new IllegalArgumentException("Not allowed to change the id property.");
        }
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<LocalisedStringEntity> getTexts() {
        return texts;
    }

    /**
     * Adds an object to the bidirectional many-to-one
     * association in both ends.
     * It is added the collection {@link #getTexts}
     * at this side and the association
     * {@link LocalisedStringEntity#setInternationalString}
     * at the opposite side is set.
     */
    public void addText(LocalisedStringEntity textElement) {
        getTexts().add(textElement);
        textElement.setInternationalString((InternationalStringEntity) this);
    }

    /**
     * Removes an object from the bidirectional many-to-one
     * association in both ends.
     * It is removed from the collection {@link #getTexts}
     * at this side and the association
     * {@link LocalisedStringEntity#setInternationalString}
     * at the opposite side is cleared (nulled).
     */
    public void removeText(LocalisedStringEntity textElement) {
        getTexts().remove(textElement);

        textElement.setInternationalString(null);

    }

    /**
     * Removes all object from the bidirectional
     * many-to-one association in both ends.
     * All elements are removed from the collection {@link #getTexts}
     * at this side and the association
     * {@link LocalisedStringEntity#setInternationalString}
     * at the opposite side is cleared (nulled).
     */
    public void removeAllTexts() {
        for (LocalisedStringEntity d : getTexts()) {
            d.setInternationalString(null);
        }

        getTexts().clear();

    }

    /**
     * This method is used by equals and hashCode.
     * 
     * @return {@link #getId}
     */
    public Object getKey() {
        return getId();
    }

    public LocalisedStringEntity getLocalisedLabelEntity(String locale) {
        if (locale == null) {
            return null;
        }
        for (LocalisedStringEntity localstr : getTexts()) {
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
        for (LocalisedStringEntity localstr : getTexts()) {
            if (locale.equalsIgnoreCase(localstr.getLocale())) {
                return localstr.getLabel();
            }
        }
        return null;
    }

    public final class Properties {

        private Properties() {
        }

        public static final String TEXTS = "texts";
    }
}
