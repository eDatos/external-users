package es.gobcan.istac.statistical.operations.roadmap.core.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.util.GeneratorUrnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.InstanceRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.InstanceService;
import es.gobcan.istac.statistical.operations.roadmap.core.service.OperationService;
import es.gobcan.istac.statistical.operations.roadmap.core.util.CheckMandatoryMetadataUtil;
import es.gobcan.istac.statistical.operations.roadmap.core.util.QueryUtil;
import es.gobcan.istac.statistical.operations.roadmap.core.util.StatisticalOperationsValidationUtils;
import es.gobcan.istac.statistical.operations.roadmap.core.util.ValidationUtil;

@Service
public class InstanceServiceImpl implements InstanceService {

    // TODO EDATOS-3124 Miguel Implement the modifications of the NeedServiceImpl.java in this class

    @Autowired
    private OperationService operationService;

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private QueryUtil queryUtil;

    @Override
    public InstanceEntity findInstanceById(Long id) {
        InstanceEntity instance = instanceRepository.findOne(id);
        if (instance == null) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_ID_NOT_FOUND);
        }
        return instance;
    }

    @Override
    public InstanceEntity findInstanceByCode(String code) {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(code, ServiceExceptionParameters.CODE, new ArrayList<>());

        InstanceEntity instance = instanceRepository.findByCode(code);
        if (instance == null) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_CODE_NOT_FOUND, code);
        }

        return instance;
    }

    @Override
    public InstanceEntity findInstanceByUrn(String urn) {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, new ArrayList<>());

        InstanceEntity instance = instanceRepository.findByUrn(urn);
        if (instance == null) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_NOT_FOUND, urn);
        }

        return instance;
    }

    @Override
    public List<InstanceEntity> findAllInstances() {
        // Repository operation
        return instanceRepository.findAll();
    }

    @Override
    public Page<InstanceEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToInstanceCriteria(pageable, query);
        return find(criteria, pageable);
    }

    @Override
    public Page<InstanceEntity> find(DetachedCriteria criteria, Pageable pageable) {
        return instanceRepository.findAll(criteria, pageable);
    }

    @Override
    public InstanceEntity createInstance(Long operationId, InstanceEntity instance) {
        // Get information
        OperationEntity operation = operationService.findOperationById(operationId);

        // Check operation for create instance. Operation PROC_STATUS can't be draft
        ValidationUtil.validateOperationProcStatusForSaveInstance(operation);

        Integer order = operation.getInstances().size();

        // Fill metadata
        instance.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationInstanceUrn(operation.getCode(), instance.getCode()));
        instance.setOperation(operation);
        instance.setOrder(order);
        instance.setProcStatus(ProcStatusEnum.DRAFT);

        // Validations
        validateInstanceCodeUnique(instance.getCode(), null);
        CheckMandatoryMetadataUtil.checkCreateInstance(instance);

        // Save instance
        instance = instanceRepository.save(instance);

        // Save operation
        operation.addInstance(instance);
        operationService.updateOperation(operation);

        // Save instance
        return findInstanceById(instance.getId());
    }

    @Override
    public InstanceEntity updateInstance(InstanceEntity instance) {
        // Validations
        OperationEntity operation = instance.getOperation();
        if (ProcStatusEnum.DRAFT.equals(instance.getProcStatus())) {
            instance.setUrn(GeneratorUrnUtils.generateSiemacStatisticalOperationInstanceUrn(operation.getCode(), instance.getCode()));
            validateInstanceCodeUnique(instance.getCode(), instance.getId());
            CheckMandatoryMetadataUtil.checkCreateInstance(instance);
        }

        if (ProcStatusEnum.INTERNALLY_PUBLISHED.equals(instance.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkInstanceForPublishInternally(instance);
            ValidationUtil.validateOperationForPublishInstanceInternally(operation);
        }

        if (ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(instance.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkInstanceForPublishExternally(instance);
            ValidationUtil.validateOperationForPublishInstanceExternally(operation);
        }

        // Operation PROC_STATUS can't be draft
        ValidationUtil.validateOperationProcStatusForSaveInstance(operation);

        // Repository operation
        return instanceRepository.save(instance);
    }

    @Override
    public List<InstanceEntity> updateInstancesOrder(Long operationId, List<Long> instancesIdList) {
        // Validations
        List<InstanceEntity> instances = operationService.findOperationById(operationId).getInstances();
        ValidationUtil.checkUpdateInstancesOrder(instancesIdList.size(), instances.size());

        // Update order
        List<InstanceEntity> instancesList = new ArrayList<>();
        Integer order = Integer.valueOf(0);

        for (Long instanceId : instancesIdList) {
            // Get instance
            InstanceEntity instance = findInstanceById(instanceId);

            // Set order
            instance.setOrder(order);

            // Add
            instancesList.add(instance);

            order++;
        }

        // Save operation
        OperationEntity operation = operationService.findOperationById(operationId);
        operation.getInstances().clear();
        operation.getInstances().addAll(instancesList);

        operation = operationService.updateOperation(operation);

        return operation.getInstances();
    }

    @Override
    public void deleteInstance(Long instanceId) {
        // Retrieve
        InstanceEntity instance = findInstanceById(instanceId);

        // Check if ProcStatus is DRAFT
        ValidationUtil.validateProcStatus(ProcStatusEnum.DRAFT, instance.getProcStatus());

        // Remove instance-operation relation
        OperationEntity operation = operationService.findOperationById(instance.getOperation().getId());
        operation.removeInstance(instance);

        // Remove instance
        instanceRepository.delete(instance);

        // Reset order of instances
        List<InstanceEntity> instances = operation.getInstances();
        Collections.reverse(instances);

        List<Long> instancesIdList = new ArrayList<Long>();

        for (InstanceEntity item : instances) {
            instancesIdList.add(item.getId());
        }
        updateInstancesOrder(operation.getId(), instancesIdList);
    }

    @Override
    public InstanceEntity publishInternallyInstance(Long id) {
        // Validations

        // Retrieve
        InstanceEntity instance = findInstanceById(id);

        // Check procStatus
        ValidationUtil.validateInstanceProcStatusForPublishInternally(instance);

        // Change procStatus
        instance.setProcStatus(ProcStatusEnum.INTERNALLY_PUBLISHED);

        // Fill metadata
        instance.setInternalInventoryDate(Instant.now());

        // Save instance
        return updateInstance(instance);
    }

    @Override
    public InstanceEntity publishExternallyInstance(Long id) {
        // Validations

        // Retrieve
        InstanceEntity instance = findInstanceById(id);

        // Check procStatus
        ValidationUtil.validateProcStatus(ProcStatusEnum.INTERNALLY_PUBLISHED, instance.getProcStatus());

        // Change procStatus
        instance.setProcStatus(ProcStatusEnum.EXTERNALLY_PUBLISHED);

        // Fill metadata
        instance.setInventoryDate(Instant.now());

        // Return
        return updateInstance(instance);
    }

    private void validateInstanceCodeUnique(String code, Long actualId) {
        if ((actualId == null && instanceRepository.existsByCode(code)) || (actualId != null && instanceRepository.existsByCodeAndIdNot(code, actualId))) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_ALREADY_EXIST_CODE_DUPLICATED, code);
        }
    }
}
