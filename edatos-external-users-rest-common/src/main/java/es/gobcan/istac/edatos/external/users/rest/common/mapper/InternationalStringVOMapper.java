package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.rest.common.dto.InternationalStringDto;

@Mapper(componentModel = "spring")
public interface InternationalStringVOMapper extends EntityMapper<InternationalStringDto, InternationalStringVO> {

    @Override
    InternationalStringDto toDto(InternationalStringVO entity);

    @Override
    InternationalStringVO toEntity(InternationalStringDto dto);
}
