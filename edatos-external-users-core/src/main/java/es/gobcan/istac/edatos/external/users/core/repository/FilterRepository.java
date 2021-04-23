package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;

@Repository
public interface FilterRepository extends JpaRepository<FilterEntity, Long> {

    Page<FilterEntity> findAll(DetachedCriteria criteria, Pageable pageable);

    List<FilterEntity> findAll(DetachedCriteria criteria);

    List<FilterEntity> findAllByExternalUserOrderByCreatedDate(ExternalUserEntity user);

    void deleteAllByExternalUser(ExternalUserEntity user);
}
