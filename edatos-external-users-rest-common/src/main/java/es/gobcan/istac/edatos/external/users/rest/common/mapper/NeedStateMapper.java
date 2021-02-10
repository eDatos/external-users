package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.edatos.external.users.core.domain.NeedStateEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.NeedStateDto;

@Mapper(config = TableValueMapper.class)
public interface NeedStateMapper extends TableValueMapper<NeedStateDto, NeedStateEntity> {

}
