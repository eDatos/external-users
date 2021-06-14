package es.gobcan.istac.edatos.external.users.core.service.impl;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalDatasetEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalDatasetRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalDatasetService;

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
}
