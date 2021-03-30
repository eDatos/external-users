package es.gobcan.istac.edatos.external.users.core.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;

/**
 * A statistical operation is the basic statistical activity planning unit. A statistical
 * operation groups the different realizations of itself (aka 'instances') and can be integrated
 * into a group with other operations (aka, 'families').
 */
@Entity
@Table(name = "tb_operations", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Cache(usage = CacheConcurrencyStrategy.NONE)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class OperationEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_operations")
    @SequenceGenerator(name = "seq_tb_operations", sequenceName = "seq_tb_operations", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @NotNull
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private InternationalStringVO name;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private InternationalStringVO description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcStatus procStatus;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_fk")
    private CategoryEntity category;

    @Override
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
     * Semantic identifier
     */
    public String getCode() {
        return code;
    }

    /**
     * Semantic identifier
     */
    public void setCode(String code) {
        this.code = code;
    }


    public InternationalStringVO getName() {
        return name;
    }

    public void setName(InternationalStringVO name) {
        this.name = name;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public InternationalStringVO getDescription() {
        return description;
    }

    public void setDescription(InternationalStringVO description) {
        this.description = description;
    }

    public ProcStatus getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatus procStatus) {
        this.procStatus = procStatus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static final class Properties {

        private Properties() {
        }

        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String PROC_STATUS = "procStatus";
        public static final String STATUS = "status";
        public static final String DESCRIPTION = "description";
        public static final String CATEGORY = "category";
    }
}
