package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;
import org.siemac.edatos.core.common.util.GeneratorUrnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.core.util.CheckMandatoryMetadataUtil;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;
import es.gobcan.istac.edatos.external.users.core.util.StatisticalOperationsValidationUtils;
import es.gobcan.istac.edatos.external.users.core.util.ValidationUtil;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

@Service
public class OperationServiceImpl implements OperationService {

    // TODO EDATOS-3124 Miguel Implement the modifications of the NeedServiceImpl.java in this class

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private QueryUtil queryUtil;

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
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(code, ServiceExceptionParameters.CODE, new ArrayList<EDatosExceptionItem>());

        OperationEntity operation = operationRepository.findByCode(code);
        if (operation == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_CODE_NOT_FOUND, code);
        }
        return operation;
    }

    @Override
    public OperationEntity findOperationByUrn(String urn) {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, new ArrayList<EDatosExceptionItem>());

        OperationEntity operation = operationRepository.findByUrn(urn);
        if (operation == null) {
            throw new EDatosException(ServiceExceptionType.OPERATION_NOT_FOUND, urn);
        }
        return operation;
    }

    @Override
    public List<OperationEntity> findAllOperations() {
        // Repository operation
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
        // Fill metadata
        operation.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(operation.getCode()));
        operation.setProcStatus(ProcStatusEnum.DRAFT);
        operation.setStatus(OperationEntity.StatusEnum.PLANNING);
        operation.setCurrentlyActive(Boolean.FALSE);

        // Validations
        validateOperationCodeUnique(operation.getCode(), null);
        CheckMandatoryMetadataUtil.checkCreateOperation(operation);

        // Repository operation
        return operationRepository.save(operation);
    }

    @Override
    public OperationEntity updateOperation(OperationEntity operation) {
        // Validations
        if (ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
            // We don't need to update the instances URN because we can't create instances in a draft operation
            operation.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(operation.getCode()));
            validateOperationCodeUnique(operation.getCode(), operation.getId());
            CheckMandatoryMetadataUtil.checkCreateOperation(operation);
        }

        if (ProcStatusEnum.INTERNALLY_PUBLISHED.equals(operation.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkOperationForPublishInternally(operation);
        }

        if (ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(operation.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkOperationForPublishExternally(operation);
        }

        // Repository operation
        return operationRepository.save(operation);
    }

    @Override
    public void deleteOperation(Long operationId) {
        // Retrieve
        OperationEntity operation = findOperationById(operationId);

        // Check if ProcStatus is DRAFT
        ValidationUtil.validateProcStatus(ProcStatusEnum.DRAFT, operation.getProcStatus());

        // Remove related families
        if (!operation.getFamilies().isEmpty()) {
            operation.removeAllFamilies();
        }

        operationRepository.delete(operation);
    }

    @Override
    public OperationEntity publishInternallyOperation(Long id) {
        // Validations

        // Load entity
        OperationEntity operation = findOperationById(id);

        // Check ProcStatus
        ValidationUtil.validateOperationProcStatusForPublishInternally(operation);

        // Change state
        operation.setProcStatus(ProcStatusEnum.INTERNALLY_PUBLISHED);

        // Fill metadata
        operation.setInternalInventoryDate(Instant.now());

        // Repository operation
        return updateOperation(operation);
    }

    @Override
    public OperationEntity publishExternallyOperation(Long id) {
        // Validations

        // Load entity
        OperationEntity operation = findOperationById(id);

        // Check ProcStatus
        ValidationUtil.validateProcStatus(ProcStatusEnum.INTERNALLY_PUBLISHED, operation.getProcStatus());

        // Change state
        operation.setProcStatus(ProcStatusEnum.EXTERNALLY_PUBLISHED);

        // Fill metadata
        operation.setInventoryDate(Instant.now());

        // Save
        return updateOperation(operation);
    }

    private void validateOperationCodeUnique(String code, Long actualId) {
        if ((actualId == null && operationRepository.existsByCode(code)) || (actualId != null && operationRepository.existsByCodeAndIdNot(code, actualId))) {
            throw new EDatosException(ServiceExceptionType.OPERATION_ALREADY_EXIST_CODE_DUPLICATED, code);
        }
    }
}
