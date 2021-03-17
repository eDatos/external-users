package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.time.Instant;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final QueryUtil queryUtil;

    public OperationServiceImpl(OperationRepository operationRepository, QueryUtil queryUtil) {
        this.operationRepository = operationRepository;
        this.queryUtil = queryUtil;
    }

    @Override
    public OperationEntity findOperationById(Long id) {
        OperationEntity operation = operationRepository.findOne(id);
        if (operation == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_NOT_FOUND);
        }
        return operation;
    }

    @Override
    public OperationEntity findOperationByCode(String code) {
        OperationEntity operation = operationRepository.findByCode(code);
        if (operation == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_CODE_NOT_FOUND, code);
        }
        return operation;
    }

    @Override
    public OperationEntity findOperationByUrn(String urn) {
        OperationEntity operation = operationRepository.findByUrn(urn);
        if (operation == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_NOT_FOUND, urn);
        }
        return operation;
    }

    @Override
    public List<OperationEntity> findAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public Page<OperationEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToOperationCriteria(pageable, query);
        return find(criteria, pageable);
    }

    @Override
    public Page<OperationEntity> find(DetachedCriteria criteria, Pageable pageable) {
        return operationRepository.findAll(criteria, pageable);
    }

    @Override
    public OperationEntity createOperation(OperationEntity operation) {
        operation.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(operation.getCode()));
        operation.setStatus(Status.PLANNING);
        validateOperationCodeUnique(operation.getCode(), null);
        return operationRepository.save(operation);
    }

    @Override
    public OperationEntity updateOperation(OperationEntity operation) {
        return operationRepository.save(operation);
    }

    @Override
    public void deleteOperation(Long operationId) {
        OperationEntity operation = findOperationById(operationId);
        operationRepository.delete(operation);
    }

    @Override
    public OperationEntity publishInternallyOperation(Long id) {
        OperationEntity operation = findOperationById(id);
        operation.setProcStatus(ProcStatus.INTERNALLY_PUBLISHED);
        operation.setInternalInventoryDate(Instant.now());
        return updateOperation(operation);
    }

    @Override
    public OperationEntity publishExternallyOperation(Long id) {
        OperationEntity operation = findOperationById(id);
        operation.setProcStatus(ProcStatus.EXTERNALLY_PUBLISHED);
        operation.setInventoryDate(Instant.now());
        return updateOperation(operation);
    }

    private void validateOperationCodeUnique(String code, Long actualId) {
        if ((actualId == null && operationRepository.existsByCode(code)) || (actualId != null && operationRepository.existsByCodeAndIdNot(code, actualId))) {
            throw new EDatosException(ServiceExceptionType.OPERATION_ALREADY_EXIST_CODE_DUPLICATED, code);
        }
    }
}
