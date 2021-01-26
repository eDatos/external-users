package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.siemac.edatos.core.common.dto.AuditableDto;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.interfaces.AbstractAuditingEntity;

@MapperConfig(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface AuditingMapperConfig {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    AbstractAuditingEntity toEntity(AuditableDto dto);
}
