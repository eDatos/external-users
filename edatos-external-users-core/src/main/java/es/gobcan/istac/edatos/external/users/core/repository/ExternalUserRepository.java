package es.gobcan.istac.edatos.external.users.core.repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalUserRepository extends JpaRepository<ExternalUserEntity, Long> {

    Optional<ExternalUserEntity> findOneByEmail(String email);

    Optional<ExternalUserEntity> findOneByEmailAndDeletionDateIsNull(String login);

    Optional<ExternalUserEntity> findOneByEmailAndDeletionDateIsNotNull(String login);
}
