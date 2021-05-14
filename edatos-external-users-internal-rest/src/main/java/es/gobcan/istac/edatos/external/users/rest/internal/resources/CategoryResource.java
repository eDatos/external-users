package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalCategoryEntity;
import es.gobcan.istac.edatos.external.users.core.service.CategoryService;
import es.gobcan.istac.edatos.external.users.core.service.StructuralResourcesService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalCategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.CategoryMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalCategoryMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
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

    // TODO(EDATOS-3357): Add audits

    public CategoryResource(CategoryService categoryService, CategoryMapper categoryMapper, StructuralResourcesService structuralResourcesService, ExternalCategoryMapper externalCategoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.structuralResourcesService = structuralResourcesService;
        this.externalCategoryMapper = externalCategoryMapper;
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secChecker.canAccessCategory(authentication)")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryEntity category = categoryService.findCategoryById(id);
        CategoryDto externalCategoryDto = categoryMapper.toDto(category);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(externalCategoryDto));
    }

    @GetMapping
    @Timed
    @PreAuthorize("@secChecker.canAccessCategory(authentication)")
    public ResponseEntity<List<CategoryDto>> getCategory(Pageable pageable, @RequestParam(required = false) String query) {
        Page<CategoryDto> result = categoryService.find(query, pageable).map(categoryMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @GetMapping("/external-categories")
    @Timed
    @PreAuthorize("@secChecker.canAccessCategory(authentication)")
    public ResponseEntity<List<ExternalCategoryDto>> getExternalCategories() {
        List<ExternalCategoryEntity> result = structuralResourcesService.getCategories();
        return ResponseEntity.ok(externalCategoryMapper.toDtos(result));
    }

    @Timed
    @GetMapping("/tree")
    @PreAuthorize("@secChecker.canAccessCategory(authentication)")
    public ResponseEntity<List<CategoryDto>> getCategoryTree() {
        return ResponseEntity.ok(categoryMapper.toDtos(categoryService.getTree()));
    }

    @Timed
    @PutMapping("/tree")
    @PreAuthorize("@secChecker.canUpdateCategory(authentication)")
    public ResponseEntity<List<CategoryDto>> updateCategory(@RequestBody List<CategoryDto> category) {
        List<CategoryEntity> categoryEntity = categoryMapper.toEntities(category);
        List<CategoryDto> response = categoryMapper.toDtos(categoryService.updateTree(categoryEntity));
        return ResponseEntity.ok(response);
    }

    @Timed
    @PostMapping
    @PreAuthorize("@secChecker.canCreateCategory(authentication)")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto category, @RequestParam(required = false) Long parentId) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(category, parentId);
        CategoryDto response = categoryMapper.toDto(categoryService.createCategory(categoryEntity));
        return ResponseEntity.ok(response);
    }

    @Timed
    @PutMapping
    @PreAuthorize("@secChecker.canUpdateCategory(authentication)")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto category, @RequestParam(required = false) Long parentId) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(category, parentId);
        CategoryDto response = categoryMapper.toDto(categoryService.updateCategory(categoryEntity));
        return ResponseEntity.ok(response);
    }

    @Timed
    @DeleteMapping("{id}")
    @PreAuthorize("@secChecker.canDeleteCategory(authentication)")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
