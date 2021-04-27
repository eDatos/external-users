package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

public interface OperationService {

    ExternalOperationEntity findOperationById(Long id);

    List<ExternalOperationEntity> findAllOperations();

    Page<ExternalOperationEntity> find(String query, Pageable pageable);

    Page<ExternalOperationEntity> find(DetachedCriteria criteria, Pageable pageable);

    ExternalOperationEntity createOperation(ExternalOperationEntity operation);

    ExternalOperationEntity updateOperation(ExternalOperationEntity operation);

    void deleteOperation(Long operationId);

}
