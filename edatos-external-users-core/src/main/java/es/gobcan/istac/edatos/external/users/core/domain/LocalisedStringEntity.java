package es.gobcan.istac.edatos.external.users.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedEntity;

@Entity
@Table(name = "tb_localised_strings", uniqueConstraints = {@UniqueConstraint(columnNames = {"locale", "international_string_fk"})})
public class LocalisedStringEntity extends AbstractVersionedEntity {

    private static final long serialVersionUID = 5536465684062425199L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_localised_strings")
    @SequenceGenerator(name = "seq_tb_localised_strings", sequenceName = "seq_tb_localised_strings", allocationSize = 50, initialValue = 1)
    private Long id;

    @Column(name = "LABEL", nullable = false, length = 4000)
    @Length(max = 4000)
    @NotNull
    private String label;

    @Column(name = "LOCALE", nullable = false, length = 255)
    @NotNull
    private String locale;

    @Column(name = "IS_UNMODIFIABLE")
    private Boolean isUnmodifiable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "INTERNATIONAL_STRING_FK")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private InternationalStringEntity internationalString;

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

    /**
     * Label of the string.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Label of the string.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * The geographic locale of the string e.g French, Canadian French.
     */
    public String getLocale() {
        return locale;
    }

    /**
     * The geographic locale of the string e.g French, Canadian French.
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * True is LocalisedString cannot be deleted or modified
     */
    public Boolean getIsUnmodifiable() {
        return isUnmodifiable;
    }

    /**
     * True is LocalisedString cannot be deleted or modified
     */
    public void setIsUnmodifiable(Boolean isUnmodifiable) {
        this.isUnmodifiable = isUnmodifiable;
    }

    /**
     * Relation to InternationalString owner
     */
    public InternationalStringEntity getInternationalString() {
        return internationalString;
    }

    /**
     * Relation to InternationalString owner
     */
    public void setInternationalString(InternationalStringEntity internationalString) {
        this.internationalString = internationalString;
    }

    /**
     * This method is used by equals and hashCode.
     *
     * @return {@link #getId}
     */
    public Object getKey() {
        return getId();
    }

    public final class Properties {

        private Properties() {
        }

        public static final String LABEL = "label";
    }
}
