package es.gobcan.istac.edatos.external.users.core.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.InstanceEntity;

@Repository
public interface InstanceRepository extends JpaRepository<InstanceEntity, Long> {

    public InstanceEntity findByCode(String code);

    public InstanceEntity findByUrn(String urn);

    public Page<InstanceEntity> findAll(DetachedCriteria criteria, Pageable pageable);

    public boolean existsByCode(String code);

    public boolean existsByCodeAndIdNot(String code, Long id);
}
