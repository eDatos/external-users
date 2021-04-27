package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import es.gobcan.istac.edatos.external.users.rest.common.dto.StructuralResourcesTreeDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.StructuralResourcesTreeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.service.CategoryService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalCategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.CategoryMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(CategoryResource.BASE_URL)
public class CategoryResource extends AbstractResource {

    public static final String ENTITY_NAME = "category";
    public static final String BASE_URL = "/api/categories";

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final StructuralResourcesTreeMapper structuralResourcesTreeMapper;

    public CategoryResource(CategoryService categoryService, CategoryMapper categoryMapper, StructuralResourcesTreeMapper structuralResourcesTreeMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.structuralResourcesTreeMapper = structuralResourcesTreeMapper;
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secCheckerExternal.canAccessCategory(authentication)")
    public ResponseEntity<ExternalCategoryDto> getCategoryById(@PathVariable Long id) {
        ExternalCategoryEntity category = categoryService.findCategoryById(id);
        ExternalCategoryDto externalCategoryDto = categoryMapper.toDto(category);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(externalCategoryDto));
    }

    @GetMapping
    @Timed
    @PreAuthorize("@secCheckerExternal.canAccessCategory(authentication)")
    public ResponseEntity<List<ExternalCategoryDto>> getCategory(Pageable pageable, @RequestParam(required = false) String query) {
        Page<ExternalCategoryDto> result = categoryService.find(query, pageable).map(categoryMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @Timed
    @GetMapping("/tree")
    @PreAuthorize("@secCheckerExternal.canAccessCategory(authentication)")
    public ResponseEntity<List<StructuralResourcesTreeDto>> getCategoryTree() {
        return ResponseEntity.ok(structuralResourcesTreeMapper.toDto());
    }
}
