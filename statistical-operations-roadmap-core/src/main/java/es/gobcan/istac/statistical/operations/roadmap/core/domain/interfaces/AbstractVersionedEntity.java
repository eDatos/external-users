package es.gobcan.istac.statistical.operations.roadmap.core.domain.interfaces;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import es.gobcan.istac.statistical.operations.roadmap.core.optlock.OptimisticLockChecker;

@MappedSuperclass
@EntityListeners(OptimisticLockChecker.class)
public abstract class AbstractVersionedEntity implements VersionedEntity {

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