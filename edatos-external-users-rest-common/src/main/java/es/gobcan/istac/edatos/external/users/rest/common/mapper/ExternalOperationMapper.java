package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.siemac.metamac.statistical.operations.core.stream.messages.OperationAvro;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalOperationRepository;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.core.service.FilterService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalOperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {InternationalStringVOMapper.class, ExternalCategoryMapper.class})
public abstract class ExternalOperationMapper implements EntityMapper<ExternalOperationDto, ExternalOperationEntity> {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    FilterService filterService;

    @Autowired
    ExternalOperationRepository externalOperationRepository;

    @Override
    @Mapping(target = "category", source = "entity.externalCategoryUrn")
    @Mapping(target = "subscribers", expression = "java(favoriteService.getOperationSubscribers().getOrDefault(entity.getId(), 0L))")
    @Mapping(target = "numberOfFilters", expression = "java(filterService.getOperationFilters().getOrDefault(entity.getUrn(), 0L))")
    public abstract ExternalOperationDto toDto(ExternalOperationEntity entity);

    @Override
    @Mapping(target = "externalCategoryUrn", source = "dto.category.urn")
    public abstract ExternalOperationEntity toEntity(ExternalOperationDto dto);

    public ExternalOperationEntity toEntity(long id, boolean enabled, boolean notificationsEnabled) {
        ExternalOperationEntity entity = externalOperationRepository.findOne(id);
        entity.setEnabled(enabled);
        entity.setNotificationsEnabled(notificationsEnabled);
        return entity;
    }

    @Mapping(target = "name", source = "title")
    @Mapping(target = "externalCategoryUrn", source = "urn")
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "publicationDate", ignore = true)
    @Mapping(target = "optLock", ignore = true)
    @Mapping(target = "notificationsEnabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    public abstract ExternalOperationEntity toEntity(OperationAvro source);
}
