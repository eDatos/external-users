package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedStateEntity;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.NeedStateDto;

@Mapper(config = TableValueMapper.class)
public interface NeedStateMapper extends TableValueMapper<NeedStateDto, NeedStateEntity> {

}
