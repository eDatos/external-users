package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;

public interface OperationService {

    OperationEntity findOperationById(Long id);

    OperationEntity findOperationByCode(String code);

    OperationEntity findOperationByUrn(String urn);

    List<OperationEntity> findAllOperations();

    Page<OperationEntity> find(String query, Pageable pageable);

    Page<OperationEntity> find(DetachedCriteria criteria, Pageable pageable);

    OperationEntity createOperation(OperationEntity operation);

    OperationEntity updateOperation(OperationEntity operation);

    void deleteOperation(Long operationId);

    OperationEntity publishInternallyOperation(Long id);

    OperationEntity publishExternallyOperation(Long id);

}
