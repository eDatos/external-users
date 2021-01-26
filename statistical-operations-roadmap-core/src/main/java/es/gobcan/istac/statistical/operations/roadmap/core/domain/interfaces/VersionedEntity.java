package es.gobcan.istac.statistical.operations.roadmap.core.domain.interfaces;

import java.io.Serializable;

public interface VersionedEntity {

    Serializable getId();

    Long getOptLock();

    void setOptLock(Long optLock);

}