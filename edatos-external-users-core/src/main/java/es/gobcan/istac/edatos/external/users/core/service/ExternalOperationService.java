package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

public interface ExternalOperationService {

    ExternalOperationEntity create(ExternalOperationEntity operation);
    ExternalOperationEntity update(ExternalOperationEntity operation);
    void delete(String urn);
    void delete(ExternalOperationEntity operation);
}
