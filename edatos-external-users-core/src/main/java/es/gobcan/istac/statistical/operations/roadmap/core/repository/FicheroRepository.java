package es.gobcan.istac.statistical.operations.roadmap.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FicheroEntity;

@Repository
public interface FicheroRepository extends JpaRepository<FicheroEntity, Long> {

    @Modifying
    @Query("delete from FicheroEntity f where not exists (select 1 from DocumentoEntity d where d.fichero.id = f.id)")
    void deleteFicherosHuerfanos();
}
