package es.gobcan.istac.edatos.external.users.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;

/**
 * A statistical operation is the basic statistical activity planning unit. A statistical
 * operation groups the different realizations of itself (aka 'instances') and can be integrated
 * into a group with other operations (aka, 'families').
 */
@Entity
@Table(name = "tb_external_operations", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class ExternalOperationEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_external_operations")
    @SequenceGenerator(name = "seq_tb_external_operations", sequenceName = "seq_tb_external_operations", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @NotNull
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    private InternationalStringVO name;

    @NaturalId
    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String urn;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String externalCategoryUrn;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InternationalStringVO getName() {
        return name;
    }

    public void setName(InternationalStringVO name) {
        this.name = name;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getExternalCategoryUrn() {
        return externalCategoryUrn;
    }

    public void setExternalCategoryUrn(String externalCategoryUrn) {
        this.externalCategoryUrn = externalCategoryUrn;
    }

    public static final class Properties {

        private Properties() {
        }

        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String CATEGORY = "category";
    }
}
