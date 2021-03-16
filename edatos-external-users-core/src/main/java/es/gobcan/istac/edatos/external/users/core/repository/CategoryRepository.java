package es.gobcan.istac.edatos.external.users.core.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findByCode(String code);
    CategoryEntity findByUrn(String urn);
    Page<CategoryEntity> findAll(DetachedCriteria criteria, Pageable pageable);
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long id);
}
