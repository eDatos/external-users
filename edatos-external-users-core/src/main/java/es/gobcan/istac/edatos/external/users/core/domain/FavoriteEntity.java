package es.gobcan.istac.edatos.external.users.core.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import es.gobcan.istac.edatos.external.users.core.domain.interfaces.AbstractVersionedAndAuditingEntity;

/**
 * Favorites are notifications on both topics and statistical operations that the user can
 * designate to be aware of their creations, modifications and deletions.
 */
@Entity
@Table(name = "tb_favorite")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class FavoriteEntity extends AbstractVersionedAndAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_favorite")
    @SequenceGenerator(name = "seq_tb_favorite", sequenceName = "seq_tb_favorite", allocationSize = 50, initialValue = 1)
    private Long id;

    @NotNull
    @JoinColumn(name = "external_user_fk", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private ExternalUserEntity externalUser;

    @JoinColumn(name = "category_fk")
    @ManyToOne
    private CategoryEntity category;

    @JoinColumn(name = "operation_fk")
    @ManyToOne
    private OperationEntity operation;

    @Override
    public Long getId() {
        return null;
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

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public OperationEntity getOperation() {
        return operation;
    }

    public void setOperation(OperationEntity operation) {
        this.operation = operation;
    }
}
