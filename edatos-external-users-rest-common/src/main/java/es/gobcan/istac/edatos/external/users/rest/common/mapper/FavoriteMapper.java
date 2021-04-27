package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.repository.CategoryRepository;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalUserRepository;
import es.gobcan.istac.edatos.external.users.core.repository.OperationRepository;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.config.AuditingMapperConfig;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", config = AuditingMapperConfig.class, uses = {GenericMapperResolver.class, ExternalUserAccountMapper.class, CategoryMapper.class, OperationMapper.class})
public abstract class FavoriteMapper implements EntityMapper<FavoriteDto, FavoriteEntity> {

    @Autowired
    private ExternalUserRepository externalUserRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Override
    @Mapping(target = "externalUser", source = "externalUser", qualifiedByName = "externalUserToBaseDto")
    public abstract FavoriteDto toDto(FavoriteEntity entity);

    @Override
    @Mapping(target = "externalUser", source = "externalUser.id", qualifiedByName = "externalUserFromId")
    @Mapping(target = "category", source = "category.id", qualifiedByName = "categoryFromId")
    @Mapping(target = "operation", source = "operation.id", qualifiedByName = "operationFromId")
    public abstract FavoriteEntity toEntity(FavoriteDto dto);

    @Named("externalUserFromId")
    public ExternalUserEntity externalUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        return externalUserRepository.findOne(id);
    }

    @Named("categoryFromId")
    public ExternalCategoryEntity categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        return categoryRepository.findOne(id);
    }

    @Named("operationFromId")
    public ExternalOperationEntity operationFromId(Long id) {
        if (id == null) {
            return null;
        }
        return operationRepository.findOne(id);
    }
}
