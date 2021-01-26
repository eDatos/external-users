package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;
import org.siemac.edatos.core.common.util.GeneratorUrnUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.FamilyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;
import es.gobcan.istac.edatos.external.users.core.repository.FamilyRepository;
import es.gobcan.istac.edatos.external.users.core.service.FamilyService;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.core.util.CheckMandatoryMetadataUtil;
import es.gobcan.istac.edatos.external.users.core.util.QueryUtil;
import es.gobcan.istac.edatos.external.users.core.util.StatisticalOperationsValidationUtils;
import es.gobcan.istac.edatos.external.users.core.util.ValidationUtil;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

@Service
public class FamilyServiceImpl implements FamilyService {

    // TODO EDATOS-3124 Miguel Implement the modifications of the NeedServiceImpl.java in this class

    @Autowired
    private OperationService operationService;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private QueryUtil queryUtil;

    @Override
    public FamilyEntity findFamilyById(Long id) {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, new ArrayList<EDatosExceptionItem>());

        FamilyEntity family = familyRepository.findOne(id);
        if (family == null) {
            throw new EDatosException(ServiceExceptionType.FAMILY_NOT_FOUND);
        }
        return family;
    }

    @Override
    public FamilyEntity findFamilyByCode(String code) {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(code, ServiceExceptionParameters.CODE, new ArrayList<EDatosExceptionItem>());

        FamilyEntity family = familyRepository.findByCode(code);
        if (family == null) {
            throw new EDatosException(ServiceExceptionType.FAMILY_CODE_NOT_FOUND, code);
        }

        return family;
    }

    @Override
    public FamilyEntity findFamilyByUrn(String urn) {
        // Validations
        StatisticalOperationsValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, new ArrayList<EDatosExceptionItem>());

        FamilyEntity family = familyRepository.findByUrn(urn);
        if (family == null) {
            throw new EDatosException(ServiceExceptionType.FAMILY_NOT_FOUND, urn);
        }

        return family;
    }

    @Override
    public Page<FamilyEntity> find(String query, Pageable pageable) {
        DetachedCriteria criteria = queryUtil.queryToFamilyCriteria(pageable, query);
        return find(criteria, pageable);
    }

    @Override
    public Page<FamilyEntity> find(DetachedCriteria criteria, Pageable pageable) {
        return familyRepository.findAll(criteria, pageable);
    }

    @Override
    public FamilyEntity create(FamilyEntity family) {
        // Fill metadata
        family.setProcStatus(ProcStatusEnum.DRAFT);
        family.setUrn(GeneratorUrnUtils.generateSiemacStatisticalFamilyUrn(family.getCode()));

        // Validations
        validateFamilyCodeUnique(family.getCode(), null);
        CheckMandatoryMetadataUtil.checkCreateFamily(family);

        // Repository operation
        return familyRepository.save(family);
    }

    @Override
    public FamilyEntity updateFamily(FamilyEntity family) {
        // Fill metadata

        // Validations
        if (ProcStatusEnum.DRAFT.equals(family.getProcStatus())) {
            family.setUrn(GeneratorUrnUtils.generateSiemacStatisticalFamilyUrn(family.getCode()));
            validateFamilyCodeUnique(family.getCode(), family.getId());
            CheckMandatoryMetadataUtil.checkCreateFamily(family);
        }

        Set<OperationEntity> operations = family.getOperations();
        if (ProcStatusEnum.INTERNALLY_PUBLISHED.equals(family.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkFamilyForPublishInternally(family);
            ValidationUtil.validateOperationsForPublishFamilyInternally(operations);
        }

        if (ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(family.getProcStatus())) {
            CheckMandatoryMetadataUtil.checkFamilyForPublishExternally(family);
            ValidationUtil.validateOperationsForPublishFamilyExternally(operations);
        }

        // Repository operation
        return familyRepository.save(family);
    }

    @Override
    public void deleteFamily(Long familyId) {
        // Retrieve
        FamilyEntity family = findFamilyById(familyId);

        // Check if ProcStatus is DRAFT
        ValidationUtil.validateProcStatus(ProcStatusEnum.DRAFT, family.getProcStatus());

        // Remove related operations
        if (!family.getOperations().isEmpty()) {
            family.removeAllOperations();
        }

        familyRepository.delete(family);
    }

    @Override
    public List<FamilyEntity> findAllFamilies() {
        // Repository operation
        return familyRepository.findAll();
    }

    @Override
    public FamilyEntity publishInternallyFamily(Long familyId) {
        // Validations

        // Load entity
        FamilyEntity family = findFamilyById(familyId);

        // Check ProcStatus
        ValidationUtil.validateFamilyProcStatusForPublishInternally(family);

        // Change state
        family.setProcStatus(ProcStatusEnum.INTERNALLY_PUBLISHED);

        // Fill metadata
        family.setInternalInventoryDate(Instant.now());

        // Repository operation
        return updateFamily(family);
    }

    @Override
    public FamilyEntity publishExternallyFamily(Long familyId) {
        // Validations

        // Load entity
        FamilyEntity family = findFamilyById(familyId);

        // Check ProcStatus
        ValidationUtil.validateProcStatus(ProcStatusEnum.INTERNALLY_PUBLISHED, family.getProcStatus());

        // Change state
        family.setProcStatus(ProcStatusEnum.EXTERNALLY_PUBLISHED);

        // Fill metadata
        family.setInventoryDate(Instant.now());

        // Save
        return updateFamily(family);
    }

    @Override
    public void addOperationFamilyAssociation(Long familyId, Long operationId) {
        // Get operation
        OperationEntity operation = operationService.findOperationById(operationId);

        // Get family
        FamilyEntity family = findFamilyById(familyId);

        // Associate
        family.addOperation(operation);

        // Save family
        updateFamily(family);
    }

    @Override
    public void removeOperationFamilyAssociation(Long familyId, Long operationId) {
        // Get operation
        OperationEntity operation = operationService.findOperationById(operationId);

        // Get family
        FamilyEntity family = findFamilyById(familyId);

        // Remove association
        family.removeOperation(operation);

        // Save
        updateFamily(family);
    }

    private void validateFamilyCodeUnique(String code, Long actualId) {
        if ((actualId == null && familyRepository.existsByCode(code)) || (actualId != null && familyRepository.existsByCodeAndIdNot(code, actualId))) {
            throw new EDatosException(ServiceExceptionType.FAMILY_ALREADY_EXIST_CODE_DUPLICATED, code);
        }
    }
}
