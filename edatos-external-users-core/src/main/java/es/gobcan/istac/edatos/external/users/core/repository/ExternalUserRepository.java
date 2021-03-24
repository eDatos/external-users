package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.Optional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;

@Repository
public interface ExternalUserRepository extends JpaRepository<ExternalUserEntity, Long> {

    Optional<ExternalUserEntity> findOneByEmail(String email);

    Optional<ExternalUserEntity> findOneByEmailAndDeletionDateIsNull(String email);

    Optional<ExternalUserEntity> findOneByEmailAndDeletionDateIsNotNull(String email);

    Optional<ExternalUserEntity> findOneByIdAndDeletionDateIsNull(Long id);

    Optional<ExternalUserEntity> findOneByIdAndDeletionDateIsNotNull(Long id);

    Page<ExternalUserEntity> findAll(DetachedCriteria criteria, Pageable pageable);
}
