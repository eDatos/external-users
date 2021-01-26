package es.gobcan.istac.edatos.external.users.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;

@Repository
public interface ExternalItemRepository extends JpaRepository<ExternalItemEntity, Long> {

}
