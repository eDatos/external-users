package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;

@Mapper(componentModel = "spring")
public abstract class InternationalStringMapper implements EntityMapper<InternationalStringDto, InternationalStringEntity> {

    @Override
    @Mapping(target = "uuid", ignore = true)
    public abstract InternationalStringDto toDto(InternationalStringEntity entity);

    @Override
    public abstract InternationalStringEntity toEntity(InternationalStringDto dto);
}
