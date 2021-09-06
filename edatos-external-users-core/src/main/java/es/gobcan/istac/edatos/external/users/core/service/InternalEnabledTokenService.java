package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.InternalEnabledTokenEntity;

public interface InternalEnabledTokenService {

    void deleteByServiceTicket(String serviceTicket);
    boolean existsByToken(String token);
    InternalEnabledTokenEntity updateOrCreate(InternalEnabledTokenEntity enabledTokenEntity);
}
