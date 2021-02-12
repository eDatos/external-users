package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.NeedTypeEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.NeedTypeDto;

@Mapper(config = TableValueMapper.class)
public interface NeedTypeMapper extends TableValueMapper<NeedTypeDto, NeedTypeEntity> {

}
