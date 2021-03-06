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
import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    Page<FavoriteEntity> findAll(DetachedCriteria criteria, Pageable pageable);
    List<FavoriteEntity> findAll(DetachedCriteria criteria);
    List<FavoriteEntity> findByExternalUser(ExternalUserEntity user);
    List<FavoriteEntity> findByCategory(CategoryEntity category);
    List<FavoriteEntity> findByExternalOperationIn(List<ExternalOperationEntity> operation);
    Optional<FavoriteEntity> findByExternalUserAndCategory(ExternalUserEntity externalUser, CategoryEntity category);
    void deleteByExternalUser(ExternalUserEntity externalUser);
    void deleteByExternalUserAndCategory(ExternalUserEntity externalUser, CategoryEntity category);
    void deleteByExternalUserAndExternalOperation(ExternalUserEntity externalUser, ExternalOperationEntity operation);

    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(e.category.id, count(e)) from FavoriteEntity e where e.category is not null group by e.category")
    List<ImmutablePair<String, Long>> getCategoriesSubscribers();

    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair(e.externalOperation.urn, count(e)) from FavoriteEntity e where e.externalOperation is not null group by e.externalOperation.urn")
    List<ImmutablePair<String, Long>> getOperationsSubscribers();

    void deleteByCategory(CategoryEntity category);
    void deleteByExternalOperation(ExternalOperationEntity operation);
    void deleteByExternalOperationUrn(String urn);
}
