package es.gobcan.istac.edatos.external.users.core.domain.interfaces;

import java.io.Serializable;

public interface VersionedEntity {

    Serializable getId();

    Long getOptLock();

    void setOptLock(Long optLock);

}