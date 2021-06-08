package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalDatasetEntity;

public interface ExternalDatasetService {

    ExternalDatasetEntity create(ExternalDatasetEntity dataset);
    ExternalDatasetEntity update(ExternalDatasetEntity dataset);
    void delete(String urn);
    void delete(ExternalDatasetEntity dataset);
}
