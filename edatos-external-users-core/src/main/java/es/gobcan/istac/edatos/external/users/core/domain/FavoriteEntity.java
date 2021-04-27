package es.gobcan.istac.edatos.external.users.core.domain;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

/**
 * Favorites are notifications on both topics and statistical operations that the user can
 * designate to be aware of certain events related to them, like creations, modifications,
 * and deletions.
 */
@Entity
@Table(name = "tb_favorites", uniqueConstraints = {@UniqueConstraint(columnNames = {"external_user_fk", "external_category_fk"}), @UniqueConstraint(columnNames = {"external_user_fk", "external_operation_fk"})})
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class FavoriteEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_favorites")
    @SequenceGenerator(name = "seq_tb_favorites", sequenceName = "seq_tb_favorites", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @JoinColumn(name = "external_user_fk", nullable = false)
    @ManyToOne(optional = false)
    private ExternalUserEntity externalUser;

    @JoinColumn(name = "external_category_fk")
    @ManyToOne
    private ExternalCategoryEntity category;

    @JoinColumn(name = "external_operation_fk")
    @ManyToOne
    private ExternalOperationEntity operation;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExternalUserEntity getExternalUser() {
        return externalUser;
    }

    public void setExternalUser(ExternalUserEntity user) {
        this.externalUser = user;
    }

    public ExternalCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(ExternalCategoryEntity category) {
        this.category = category;
    }

    public ExternalOperationEntity getOperation() {
        return operation;
    }

    public void setOperation(ExternalOperationEntity operation) {
        this.operation = operation;
    }
}
