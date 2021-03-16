package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siemac.edatos.core.common.dto.InternationalStringDto;
import org.siemac.edatos.core.common.dto.LocalisedStringDto;

import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.domain.vo.LocalisedStringVO;

@Mapper(componentModel = "spring")
public interface InternationalStringVOMapper extends EntityMapper<InternationalStringDto, InternationalStringVO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "optLock", ignore = true)
    InternationalStringDto toDto(InternationalStringVO entity);

    @Override
    InternationalStringVO toEntity(InternationalStringDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "optLock", ignore = true)
    @Mapping(target = "isUnmodifiable", ignore = true)
    LocalisedStringDto localisedStringVOToLocalisedStringDto(LocalisedStringVO localisedStringVO);
}
