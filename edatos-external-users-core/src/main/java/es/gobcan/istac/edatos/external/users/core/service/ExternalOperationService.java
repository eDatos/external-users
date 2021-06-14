package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

public interface ExternalOperationService {

    ExternalOperationEntity create(ExternalOperationEntity operation);
    ExternalOperationEntity update(ExternalOperationEntity operation);
    void delete(String urn);
    void delete(ExternalOperationEntity operation);
    List<ExternalOperationEntity> findByExternalCategoryUrnIn(List<String> urns);
    ExternalOperationEntity updateNotifications(ExternalOperationEntity externalOperation);
}
