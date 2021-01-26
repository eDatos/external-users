package es.gobcan.istac.statistical.operations.roadmap.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.InternationalStringEntity;

@Repository
public interface InternationalStringRepository extends JpaRepository<InternationalStringEntity, Long> {

}