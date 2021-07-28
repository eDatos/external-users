package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalDatasetEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalDatasetRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalDatasetService;

@Service
public class ExternalDatasetServiceImpl implements ExternalDatasetService {

    private final ExternalDatasetRepository externalDatasetRepository;

    public ExternalDatasetServiceImpl(ExternalDatasetRepository externalDatasetRepository) {
        this.externalDatasetRepository = externalDatasetRepository;
    }

    @Override
    public ExternalDatasetEntity create(ExternalDatasetEntity dataset) {
        return externalDatasetRepository.saveAndFlush(dataset);
    }

    @Override
    public ExternalDatasetEntity update(ExternalDatasetEntity dataset) {
        return externalDatasetRepository.saveAndFlush(dataset);
    }

    @Override
    public void delete(String urn) {
        externalDatasetRepository.deleteByUrn(urn);
    }

    @Override
    public void delete(ExternalDatasetEntity dataset) {
        externalDatasetRepository.delete(dataset);
    }

    @Override
    public Optional<ExternalDatasetEntity> findByUrn(String urn) {
        return externalDatasetRepository.findByUrn(urn);
    }
}
