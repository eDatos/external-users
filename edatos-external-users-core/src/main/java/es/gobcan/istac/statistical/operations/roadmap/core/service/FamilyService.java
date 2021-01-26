package es.gobcan.istac.statistical.operations.roadmap.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;

public interface FamilyService {

    FamilyEntity findFamilyById(Long id);

    FamilyEntity findFamilyByCode(String code);

    FamilyEntity findFamilyByUrn(String urn);

    List<FamilyEntity> findAllFamilies();

    Page<FamilyEntity> find(String query, Pageable pageable);

    Page<FamilyEntity> find(DetachedCriteria criteria, Pageable pageable);

    FamilyEntity create(FamilyEntity family);

    FamilyEntity updateFamily(FamilyEntity family);

    void deleteFamily(Long id);

    FamilyEntity publishInternallyFamily(Long id);

    FamilyEntity publishExternallyFamily(Long id);

    void addOperationFamilyAssociation(Long familyId, Long operationId);

    void removeOperationFamilyAssociation(Long familyId, Long operationId);

}