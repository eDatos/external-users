package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.MapperConfig;

import es.gobcan.istac.edatos.external.users.core.domain.TableValueEntity;
import es.gobcan.istac.edatos.external.users.rest.common.dto.TableValueDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@MapperConfig(componentModel = "spring", uses = {GenericMapperResolver.class})
public interface TableValueMapper<D extends TableValueDto, E extends TableValueEntity> extends EntityMapper<D, E> {

    E toEntity(D dto);
}
