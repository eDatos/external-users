package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    Page<FavoriteEntity> findAll(DetachedCriteria criteria, Pageable pageable);

    List<FavoriteEntity> findAll(DetachedCriteria criteria);

    List<FavoriteEntity> findAllByExternalUserOrderByCreatedDate(ExternalUserEntity user);

    void deleteByExternalUser(ExternalUserEntity externalUser);
    void deleteByExternalUserAndCategory(ExternalUserEntity externalUser, CategoryEntity category);
    void deleteByExternalUserAndOperation(ExternalUserEntity externalUser, OperationEntity operation);
}
