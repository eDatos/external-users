package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalDatasetEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalDatasetDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {InternationalStringVOMapper.class, ExternalOperationMapper.class})
public abstract class ExternalDatasetMapper implements EntityMapper<ExternalDatasetDto, ExternalDatasetEntity> {

    @Mapping(target = "name",
             source = "siemacMetadataStatisticalResource.lifecycleStatisticalResource.versionableStatisticalResource.nameableStatisticalResource.title")
    @Mapping(target = "urn",
             source = "siemacMetadataStatisticalResource.lifecycleStatisticalResource.versionableStatisticalResource.nameableStatisticalResource.identifiableStatisticalResource.urn")
    @Mapping(target = "code",
             source = "siemacMetadataStatisticalResource.lifecycleStatisticalResource.versionableStatisticalResource.nameableStatisticalResource.identifiableStatisticalResource.code")
    @Mapping(target = "externalOperationUrn",
             source = "siemacMetadataStatisticalResource.lifecycleStatisticalResource.versionableStatisticalResource.nameableStatisticalResource.identifiableStatisticalResource.statisticalOperation.urn")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "optLock", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    public abstract ExternalDatasetEntity toEntity(DatasetVersionAvro value);
}
