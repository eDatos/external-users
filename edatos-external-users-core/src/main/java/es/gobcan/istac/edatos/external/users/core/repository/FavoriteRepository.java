package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Optional<FavoriteEntity> findByExternalUserAndCategory(ExternalUserEntity externalUser, CategoryEntity category);
    void deleteByExternalUserAndCategory(ExternalUserEntity externalUser, CategoryEntity category);
    void deleteByExternalUserAndOperation(ExternalUserEntity externalUser, OperationEntity operation);

    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(e.category.id, count(e)) from FavoriteEntity e group by e.category")
    List<ImmutablePair<Long, Long>> getCategorySubscribers();
}
