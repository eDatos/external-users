package es.gobcan.istac.edatos.external.users.core.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.IssuesEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;

@Repository
public interface IssuesRepository extends JpaRepository<IssuesEntity, Long> {

	Page<UsuarioEntity> findAll(DetachedCriteria criteria, Pageable pageable);
}
