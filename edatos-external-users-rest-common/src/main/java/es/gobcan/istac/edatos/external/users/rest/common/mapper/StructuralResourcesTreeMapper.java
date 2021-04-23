package es.gobcan.istac.edatos.external.users.rest.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.CategoryService;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.StructuralResourcesTreeDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.resolver.GenericMapperResolver;

@Mapper(componentModel = "spring", uses = {GenericMapperResolver.class, InternationalStringVOMapper.class})
public abstract class StructuralResourcesTreeMapper {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OperationService operationService;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    OperationMapper operationMapper;

    @Autowired
    InternationalStringVOMapper internationalStringVOMapper;

    public List<StructuralResourcesTreeDto> toDto() {
        List<StructuralResourcesTreeDto> dto = new ArrayList<>();
        for (CategoryEntity category : categoryService.getTree()) {
            dto.add(createTree(category));
        }
        return dto;
    }

    private StructuralResourcesTreeDto createTree(CategoryEntity category) {
        StructuralResourcesTreeDto dto = new StructuralResourcesTreeDto();
        dto.setId(category.getId());
        dto.setName(internationalStringVOMapper.toDto(category.getName()));
        dto.setCode(category.getCode());
        dto.setType("category");
        dto.setSubscribers(favoriteService.getCategorySubscribers().getOrDefault(category.getId(), 0L));
        dto.setChildren(new ArrayList<>());

        for (OperationEntity op : category.getOperations()) {
            dto.getChildren().add(fromOperationToStructuralResourcesTreeDto(op));
        }
        for (CategoryEntity cat : category.getChildren()) {
            dto.getChildren().add(createTree(cat));
        }

        return dto;
    }

    protected StructuralResourcesTreeDto fromOperationToStructuralResourcesTreeDto(OperationEntity operation) {
        StructuralResourcesTreeDto dto = new StructuralResourcesTreeDto();

        dto.setId(operation.getId());
        dto.setName(internationalStringVOMapper.toDto(operation.getName()));
        dto.setCode(operation.getCode());
        dto.setType("operation");
        dto.setSubscribers(favoriteService.getOperationSubscribers().getOrDefault(operation.getId(), 0L));

        return dto;
    }
}
