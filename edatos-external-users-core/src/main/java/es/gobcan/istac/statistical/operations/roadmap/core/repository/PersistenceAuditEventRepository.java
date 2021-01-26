package es.gobcan.istac.statistical.operations.roadmap.core.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.AuditEventEntity;

public interface PersistenceAuditEventRepository extends JpaRepository<AuditEventEntity, Long> {

    List<AuditEventEntity> findByPrincipal(String principal);

    List<AuditEventEntity> findByAuditEventDateAfter(Instant after);

    List<AuditEventEntity> findByPrincipalAndAuditEventDateAfter(String principal, Instant after);

    List<AuditEventEntity> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principle, Instant after, String type);

    Page<AuditEventEntity> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);
}
