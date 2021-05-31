package es.gobcan.istac.edatos.external.users.core.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.DisabledTokenEntity;

@Repository
public interface DisabledTokenRepository extends JpaRepository<DisabledTokenEntity, String> {

    public void deleteByExpirationDateBefore(Instant date);
}
