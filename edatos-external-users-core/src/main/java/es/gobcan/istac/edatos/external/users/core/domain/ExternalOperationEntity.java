package es.gobcan.istac.edatos.external.users.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.siemac.edatos.core.common.enume.TypeExternalArtefactsEnum;

/**
 * A statistical operation is the basic statistical activity planning unit. A statistical
 * operation groups the different realizations of itself (aka 'instances') and can be integrated
 * into a group with other operations (aka, 'families').
 */
@Entity
@Table(name = "tb_external_operations")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name="external_item_fk")
public class ExternalOperationEntity extends ExternalItemEntity {

    public ExternalOperationEntity() {
        type = TypeExternalArtefactsEnum.STATISTICAL_OPERATION;
    }

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String externalCategoryUrn;

    @NotNull
    @Column(nullable = false)
    private boolean notificationsEnabled;

    public String getExternalCategoryUrn() {
        return externalCategoryUrn;
    }

    public void setExternalCategoryUrn(String externalCategoryUrn) {
        this.externalCategoryUrn = externalCategoryUrn;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public static final class Properties {

        private Properties() {
        }

        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String CATEGORY = "category";
    }
}
