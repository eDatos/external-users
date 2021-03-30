package es.gobcan.istac.edatos.external.users.core.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Long> {

    Page<OperationEntity> findAll(DetachedCriteria criteria, Pageable pageable);
}
