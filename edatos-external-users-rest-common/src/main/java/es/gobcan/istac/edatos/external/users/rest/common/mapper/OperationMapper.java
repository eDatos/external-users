package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.OperationBaseDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.OperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, ExternalItemMapper.class, InternationalStringVOMapper.class, CategoryMapper.class, OperationRepository.class})
public abstract class OperationMapper implements EntityMapper<OperationDto, OperationEntity> {

    public abstract OperationBaseDto toBaseDto(OperationEntity source);

    public abstract OperationDto toDto(OperationEntity source);

    public abstract OperationEntity toEntity(OperationDto source);
}
