package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> deleteById(Long id);
    Page<CategoryEntity> findAll(DetachedCriteria criteria, Pageable pageable);
    List<CategoryEntity> getByParentIsNull();
}
