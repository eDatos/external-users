package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.util.List;
import java.util.Optional;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.service.StructuralResourcesService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalCategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalCategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.service.CategoryService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
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
    private final StructuralResourcesService structuralResourcesService;
    private final ExternalCategoryMapper externalCategoryMapper;

    public CategoryResource(CategoryService categoryService, StructuralResourcesService structuralResourcesService, CategoryMapper categoryMapper, ExternalCategoryMapper externalCategoryMapper) {
        this.categoryService = categoryService;
        this.structuralResourcesService = structuralResourcesService;
        this.categoryMapper = categoryMapper;
        this.externalCategoryMapper = externalCategoryMapper;
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secCheckerExternal.canAccessCategory(authentication)")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryEntity category = categoryService.findCategoryById(id);
        CategoryDto externalCategoryDto = categoryMapper.toDto(category);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(externalCategoryDto));
    }

    @GetMapping
    @Timed
    @PreAuthorize("@secCheckerExternal.canAccessCategory(authentication)")
    public ResponseEntity<List<CategoryDto>> getCategory(Pageable pageable, @RequestParam(required = false) String query) {
        Page<CategoryDto> result = categoryService.find(query, pageable).map(categoryMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @Timed
    @GetMapping("/tree")
    @PreAuthorize("@secCheckerExternal.canAccessCategory(authentication)")
    public ResponseEntity<List<CategoryDto>> getCategoryTree() {
        return ResponseEntity.ok(categoryMapper.toDtos(categoryService.getTree()));
    }

    @Timed
    @PutMapping("/tree")
    @PreAuthorize("@secCheckerExternal.canAccessCategory(authentication)")
    public ResponseEntity<List<CategoryDto>> updateCategoryTree(@RequestBody List<CategoryDto> tree) {
        List<CategoryEntity> categories = categoryMapper.toEntities(tree);
        List<CategoryDto> response = categoryMapper.toDtos(categoryService.updateTree(categories));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/external")
    @Timed
    @PreAuthorize("@secCheckerExternal.canUpdateCategoryTree(authentication)")
    public ResponseEntity<List<ExternalCategoryDto>> getExternalCategories() {
        List<ExternalCategoryEntity> result = structuralResourcesService.getCategories();
        return ResponseEntity.ok(externalCategoryMapper.toDtos(result));
    }
}
