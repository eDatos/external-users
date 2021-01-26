package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.NeedBaseDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.NeedDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.resolver.GenericMapperResolver;

@Mapper(config = AuditingMapperConfig.class, componentModel = "spring", uses = {NeedTypeMapper.class, NeedStateMapper.class, GenericMapperResolver.class})
public interface NeedMapper extends EntityMapper<NeedDto, NeedEntity> {

    NeedEntity toEntity(NeedDto dto);

    NeedBaseDto toBaseDto(NeedEntity entity);

    @IterableMapping(elementTargetType = NeedBaseDto.class)
    List<NeedBaseDto> toBaseDtos(List<NeedEntity> entity);
}
