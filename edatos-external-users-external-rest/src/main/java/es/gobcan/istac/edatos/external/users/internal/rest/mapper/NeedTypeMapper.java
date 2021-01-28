package es.gobcan.istac.edatos.external.users.internal.rest.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.NeedTypeEntity;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.NeedTypeDto;

@Mapper(config = TableValueMapper.class)
public interface NeedTypeMapper extends TableValueMapper<NeedTypeDto, NeedTypeEntity> {

}
