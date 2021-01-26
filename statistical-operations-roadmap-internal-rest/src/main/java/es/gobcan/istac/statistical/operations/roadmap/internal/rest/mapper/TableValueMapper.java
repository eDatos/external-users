package es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper;

import org.mapstruct.MapperConfig;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.TableValueEntity;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.TableValueDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.resolver.GenericMapperResolver;

@MapperConfig(componentModel = "spring", uses = {GenericMapperResolver.class})
public interface TableValueMapper<D extends TableValueDto, E extends TableValueEntity> extends EntityMapper<D, E> {

    E toEntity(D dto);
}
