package es.gobcan.istac.edatos.external.users.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.domain.UsuarioEntity;

@Repository
public interface FilterRepository extends JpaRepository<FilterEntity, Long> {

    List<FilterEntity> findAllByUserOrderByCreatedDate(UsuarioEntity user);
}
