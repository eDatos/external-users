package es.gobcan.istac.edatos.external.users.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedEntity;

@Entity
@Table(name = "tb_lis_instance_types")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class InstanceTypeEntity extends AbstractVersionedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_lis_instance_types")
    @SequenceGenerator(name = "seq_tb_lis_instance_types", sequenceName = "seq_tb_lis_instance_types", allocationSize = 50, initialValue = 1)
    private Long id;

    @Column(name = "identifier", nullable = false, length = 255)
    @NotNull
    private String identifier;

    @Column(name = "uuid", nullable = false, length = 36, unique = true)
    private String uuid;

    @ManyToOne()
    @JoinColumn(name = "description", foreignKey = @ForeignKey(name = "fk_tb_lis_instance_types_des38"))
    private InternationalStringEntity description;

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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * This domain object doesn't have a natural key
     * and this random generated identifier is the
     * unique identifier for this domain object.
     */
    public String getUuid() {

        // lazy init of UUID
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
        }
        return uuid;
    }

    public InternationalStringEntity getDescription() {
        return description;
    }

    public void setDescription(InternationalStringEntity description) {
        this.description = description;
    }

    @PrePersist
    protected void prePersist() {
        getUuid();
    }

    /**
     * This method is used by equals and hashCode.
     * 
     * @return {{@link #getUuid}
     */
    public Object getKey() {
        return getUuid();
    }
    
    public final class Properties {

        private Properties() {
        }

        public static final String IDENTIFIER = "identifier";
    }
}
