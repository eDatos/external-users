package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.OperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {GenericMapperResolver.class, InternationalStringVOMapper.class, CategoryMapper.class, OperationRepository.class})
public abstract class OperationMapper implements EntityMapper<OperationDto, ExternalOperationEntity> {

    public abstract OperationDto toDto(ExternalOperationEntity source);

    public abstract ExternalOperationEntity toEntity(OperationDto source);
}
