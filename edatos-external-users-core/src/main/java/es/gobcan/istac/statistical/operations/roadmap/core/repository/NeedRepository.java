package es.gobcan.istac.statistical.operations.roadmap.core.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity;

@Repository
public interface NeedRepository extends JpaRepository<NeedEntity, Long> {

    public Page<NeedEntity> findAll(DetachedCriteria criteria, Pageable pageable);

    public List<NeedEntity> findAll(DetachedCriteria criteria);

    public Long countByIdNotAndCode(Long id, String code);
    public Long countByCode(String code);

}
