package es.gobcan.istac.edatos.external.users.core.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.FamilyEntity;

@Repository
public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

    public FamilyEntity findByCode(String code);

    public FamilyEntity findByUrn(String urn);

    public Page<FamilyEntity> findAll(DetachedCriteria criteria, Pageable pageable);

    public boolean existsByCode(String code);

    public boolean existsByCodeAndIdNot(String code, Long id);
}
