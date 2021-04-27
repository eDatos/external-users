package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;

@Repository
public interface OperationRepository extends JpaRepository<ExternalOperationEntity, Long> {

    Page<ExternalOperationEntity> findAll(DetachedCriteria criteria, Pageable pageable);
    List<ExternalOperationEntity> getByCategory(ExternalCategoryEntity parent);
}
