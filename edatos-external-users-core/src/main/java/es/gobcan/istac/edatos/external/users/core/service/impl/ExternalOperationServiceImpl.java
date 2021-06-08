package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalOperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalOperationService;

@Service
public class ExternalOperationServiceImpl implements ExternalOperationService {

    private final ExternalOperationRepository externalOperationRepository;

    public ExternalOperationServiceImpl(ExternalOperationRepository externalOperationRepository) {
        this.externalOperationRepository = externalOperationRepository;
    }

    @Override
    public ExternalOperationEntity create(ExternalOperationEntity operation) {
        return externalOperationRepository.saveAndFlush(operation);
    }

    @Override
    public ExternalOperationEntity update(ExternalOperationEntity operation) {
        return externalOperationRepository.saveAndFlush(operation);
    }

    @Override
    public void delete(String urn) {
        externalOperationRepository.deleteByUrn(urn);
    }

    @Override
    public void delete(ExternalOperationEntity operation) {
        externalOperationRepository.delete(operation);
    }
}
