package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalOperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {GenericMapperResolver.class, InternationalStringVOMapper.class, ExternalCategoryMapper.class})
public abstract class ExternalOperationMapper implements EntityMapper<ExternalOperationDto, ExternalOperationEntity> {

    @Autowired
    FavoriteService favoriteService;

    @Override
    @Mapping(target = "category", source = "entity.externalCategoryUrn")
    @Mapping(target = "subscribers", expression = "java(favoriteService.getOperationSubscribers().getOrDefault(entity.getId(), 0L))")
    public abstract ExternalOperationDto toDto(ExternalOperationEntity entity);

    @Override
    @Mapping(target = "externalCategoryUrn", source = "dto.category.urn")
    @Mapping(target = "categories", ignore = true)
    public abstract ExternalOperationEntity toEntity(ExternalOperationDto dto);
}
