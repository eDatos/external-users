package es.gobcan.istac.edatos.external.users.core.repository;

import org.joda.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalDatasetEntity;

@Repository
public interface ExternalDatasetRepository extends AbstractExternalItemRepository<ExternalDatasetEntity> {

    List<ExternalDatasetEntity> findByExternalOperationUrn(String urn);
    List<ExternalDatasetEntity> findByExternalOperationUrnIn(List<String> urns);
    Optional<ExternalDatasetEntity> findByCode(String code);

    @Query("SELECT ds FROM ExternalDatasetEntity ds WHERE ds.createdDate <= :startDate AND ds.createdDate >= :endDate")
    List<ExternalDatasetEntity> findAllByChangeRegisterOfDataset(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
