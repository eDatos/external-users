package es.gobcan.istac.statistical.operations.roadmap.core.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;

public interface InstanceService {

    InstanceEntity findInstanceById(Long id);

    InstanceEntity findInstanceByCode(String code);

    InstanceEntity findInstanceByUrn(String urn);

    List<InstanceEntity> findAllInstances();

    Page<InstanceEntity> find(String query, Pageable pageable);

    Page<InstanceEntity> find(DetachedCriteria detachedCriteria, Pageable pageable);

    InstanceEntity createInstance(Long operationId, InstanceEntity instance);

    InstanceEntity updateInstance(InstanceEntity instance);

    List<InstanceEntity> updateInstancesOrder(Long operationId, List<Long> instancesIdList);

    void deleteInstance(Long instanceId);

    InstanceEntity publishInternallyInstance(Long id);

    InstanceEntity publishExternallyInstance(Long id);
}
