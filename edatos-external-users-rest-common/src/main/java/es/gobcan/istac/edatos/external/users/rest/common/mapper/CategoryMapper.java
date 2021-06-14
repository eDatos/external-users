package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalCategoryRepository;
import es.gobcan.istac.edatos.external.users.core.service.ExternalCategoryService;
import es.gobcan.istac.edatos.external.users.core.service.ExternalOperationService;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalCategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalOperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {InternationalStringVOMapper.class, ExternalCategoryMapper.class})
public abstract class CategoryMapper implements EntityMapper<CategoryDto, CategoryEntity> {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    ExternalCategoryService externalCategoryService;

    @Autowired
    ExternalCategoryRepository externalCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ExternalOperationMapper externalOperationMapper;

    @Autowired
    ExternalOperationService externalOperationService;

    @Override
    @Mapping(target = "subscribers", expression = "java(favoriteService.getCategorySubscribers().getOrDefault(entity.getId(), 0L))")
    @Mapping(target = "externalCategories", source = "entity.externalCategories")
    @Mapping(target = "externalOperations", source = "externalCategories", qualifiedByName = "getOperations")
    public abstract CategoryDto toDto(CategoryEntity entity);

    @Override
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "externalCategories", source = "dto.externalCategories", qualifiedByName = "getExternalCategoryEntitiesFromUrn")
    public abstract CategoryEntity toEntity(CategoryDto dto);

    @Named("getOperations")
    public List<ExternalOperationDto> getOperations(Collection<ExternalCategoryEntity> entities) {
        List<String> urns = entities.stream().map(ExternalCategoryEntity::getUrn).collect(Collectors.toList());
        List<ExternalOperationEntity> externalOperations = externalOperationService.findByExternalCategoryUrnIn(urns);
        return externalOperations.stream().map(externalOperationMapper::toDto).collect(Collectors.toList());
    }

    @Named("getExternalCategoryEntitiesFromUrn")
    public Set<ExternalCategoryEntity> getExternalCategoryEntitiesFromUrn(List<ExternalCategoryDto> resources) {
        List<String> urns = resources.stream().map(ExternalCategoryDto::getUrn).filter(Objects::nonNull).collect(Collectors.toList());
        List<ExternalCategoryEntity> inDbExternalCategories = externalCategoryService.findAll();
        // @formatter:off
        return externalCategoryService.requestAllExternalCategories().stream()
                // pick only the selected external categories
                .filter(externalCategory -> urns.contains(externalCategory.getUrn()))
                // if the external category was already in db, pick that one; otherwise create a new one (this avoid problems with unique constraints and duplication)
                .map(newExternalCategory -> inDbExternalCategories.stream()
                                                                  .filter(inDbExternalCategory -> Objects.equals(inDbExternalCategory.getUrn(), newExternalCategory.getUrn()))
                                                                  .findAny()
                                                                  .orElse(newExternalCategory))
                .collect(Collectors.toSet());
        // @formatter:on
    }
}
