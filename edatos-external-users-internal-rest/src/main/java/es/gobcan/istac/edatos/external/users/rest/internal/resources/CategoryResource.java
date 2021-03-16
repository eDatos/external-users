package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.util.List;

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

import es.gobcan.istac.edatos.external.users.core.service.CategoryService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.CategoryDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.CategoryMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.PaginationUtil;

@RestController
@RequestMapping(CategoryResource.BASE_URL)
public class CategoryResource extends AbstractResource {

    public static final String ENTITY_NAME = "category";
    public static final String BASE_URL = "/api/categories";

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryResource(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secChecker.canAccessCategory(authentication)")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.findCategoryById(id)));
    }

    @GetMapping
    @Timed
    @PreAuthorize("@secChecker.canAccessCategory(authentication)")
    public ResponseEntity<List<CategoryDto>> getCategory(Pageable pageable, @RequestParam(required = false) String query) {
        // TODO(EDATOS-3294): Does it make sense that this is a page? Categories are a tree, doesn't fit well into a
        //  page/List structure.
        Page<CategoryDto> result = categoryService.find(query, pageable).map(categoryMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }
}
