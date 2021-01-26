package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.NeedTypeDto;

@Mapper(config = TableValueMapper.class)
public interface NeedTypeMapper extends TableValueMapper<NeedTypeDto, NeedTypeEntity> {

}
