package es.gobcan.istac.statistical.operations.roadmap.core.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {

    public OperationEntity findByCode(String code);

    public OperationEntity findByUrn(String urn);

    public Page<OperationEntity> findAll(DetachedCriteria criteria, Pageable pageable);

    public boolean existsByCode(String code);

    public boolean existsByCodeAndIdNot(String code, Long id);
}
