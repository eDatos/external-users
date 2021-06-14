package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.ExternalOperationService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.ExternalOperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalOperationMapper;

@RestController
@RequestMapping(ExternalOperationResource.BASE_URL)
public class ExternalOperationResource extends AbstractResource {

    public static final String ENTITY_NAME = "externalOperation";
    public static final String BASE_URL = "/api/external-operations";

    private final ExternalOperationService externalOperationService;
    private final ExternalOperationMapper externalOperationMapper;

    public ExternalOperationResource(ExternalOperationService externalOperationService, ExternalOperationMapper externalOperationMapper) {
        this.externalOperationService = externalOperationService;
        this.externalOperationMapper = externalOperationMapper;
    }

    //@GetMapping("/{id}")
    //@Timed
    //@PreAuthorize("@secChecker.canAccessCategory(authentication)")
    //public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
    //    CategoryEntity category = categoryService.findCategoryById(id);
    //    CategoryDto externalCategoryDto = categoryMapper.toDto(category);
    //    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(externalCategoryDto));
    //}

    //@GetMapping
    //@Timed
    //@PreAuthorize("@secChecker.canAccessCategory(authentication)")
    //public ResponseEntity<List<CategoryDto>> getCategory(Pageable pageable, @RequestParam(required = false) String query) {
    //    Page<CategoryDto> result = categoryService.find(query, pageable).map(categoryMapper::toDto);
    //    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
    //    return ResponseEntity.ok().headers(headers).body(result.getContent());
    //}

    @Timed
    @PutMapping
    //@PreAuthorize("@secChecker.canUpdateCategory(authentication)")
    public ResponseEntity<ExternalOperationDto> updateExternalOperationNotifications(@RequestBody ExternalOperationDto externalOperationDto) {
        ExternalOperationEntity externalOperation = externalOperationMapper.toEntity(externalOperationDto);
        externalOperation = externalOperationService.updateNotifications(externalOperation);
        return ResponseEntity.ok(externalOperationMapper.toDto(externalOperation));
    }
}
