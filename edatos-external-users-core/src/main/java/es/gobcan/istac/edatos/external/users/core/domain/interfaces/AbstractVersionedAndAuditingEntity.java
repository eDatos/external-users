package es.gobcan.istac.edatos.external.users.core.domain.interfaces;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import es.gobcan.istac.edatos.external.users.core.optlock.OptimisticLockChecker;

@MappedSuperclass
@EntityListeners(OptimisticLockChecker.class)
public abstract class AbstractVersionedAndAuditingEntity extends AbstractAuditingEntity implements VersionedEntity {

    private static final long serialVersionUID = -3342374611675636866L;

    @Version
    @Column(name = "opt_lock")
    private Long optLock;

    @Override
    public Long getOptLock() {
        return optLock;
    }

    @Override
    public void setOptLock(Long optLock) {
        this.optLock = optLock;
    }
}