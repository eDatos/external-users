package es.gobcan.istac.edatos.external.users.core.service.impl;

import es.gobcan.istac.edatos.external.users.core.domain.InternalEnabledTokenEntity;
import es.gobcan.istac.edatos.external.users.core.repository.InternalEnabledTokenRepository;
import es.gobcan.istac.edatos.external.users.core.service.InternalEnabledTokenService;
import org.springframework.stereotype.Service;

@Service
public class InternalEnabledTokenServiceImpl implements InternalEnabledTokenService {

    private final InternalEnabledTokenRepository enabledTokenRepository;

    public InternalEnabledTokenServiceImpl(InternalEnabledTokenRepository enabledTokenRepository) {
        this.enabledTokenRepository = enabledTokenRepository;
    }

    @Override
    public void deleteByServiceTicket(String serviceTicket) {
        enabledTokenRepository.delete(serviceTicket);
    }

    @Override
    public boolean existsByToken(String token) {
        return enabledTokenRepository.existsByToken(token);
    }

    @Override
    public InternalEnabledTokenEntity updateOrCreate(InternalEnabledTokenEntity enabledTokenEntity) {
        return enabledTokenRepository.save(enabledTokenEntity);
    }
}
