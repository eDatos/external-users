package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

public interface ExternalOperationService {

    List<ExternalOperationEntity> findAll();
    Page<ExternalOperationEntity> find(String query, Pageable pageable);
    ExternalOperationEntity create(ExternalOperationEntity operation);
    ExternalOperationEntity update(ExternalOperationEntity operation);
    void delete(String urn);
    void delete(ExternalOperationEntity operation);
    List<ExternalOperationEntity> findByExternalCategoryUrnIn(List<String> urns);
}
