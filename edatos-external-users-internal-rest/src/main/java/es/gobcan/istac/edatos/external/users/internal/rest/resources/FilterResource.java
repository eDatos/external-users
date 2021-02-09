package es.gobcan.istac.edatos.external.users.internal.rest.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.service.FilterService;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.internal.rest.mapper.FilterMapper;
import es.gobcan.istac.edatos.external.users.internal.rest.util.HeaderUtil;

@RestController
@RequestMapping(FilterResource.BASE_URL)
public class FilterResource extends AbstractResource {

    public static final String ENTITY_NAME = "filter";
    public static final String BASE_URL = "/api/filters";

    private final FilterService filterService;

    private final FilterMapper filterMapper;

    private final AuditEventPublisher auditPublisher;

    public FilterResource(FilterService filterService, FilterMapper filterMapper, AuditEventPublisher auditPublisher) {
        this.filterService = filterService;
        this.filterMapper = filterMapper;
        this.auditPublisher = auditPublisher;
    }

    @GetMapping
    @Timed
    @PreAuthorize("@secChecker.canListAllFilters(authentication)")
    public ResponseEntity<List<FilterDto>> getAllFilters() {
        return ResponseEntity.ok(filterMapper.toDtos(filterService.findAll()));
    }

    @PostMapping
    @Timed
    @PreAuthorize("@secChecker.canCreateFilters(authentication)")
    public ResponseEntity<FilterDto> createFilter(@RequestBody FilterDto dto) throws URISyntaxException {
        FilterEntity entity = filterMapper.toEntity(dto);
        entity = filterService.create(entity);
        FilterDto newDto = filterMapper.toDto(entity);

        auditPublisher.publish(AuditConstants.FILTER_CREATION, newDto.getLogin());
        return ResponseEntity.created(new URI(BASE_URL + "/" + newDto.getId()))
                             .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, newDto.getId().toString()))
                             .body(newDto);
    }

    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("@secChecker.canCreateFilters(authentication)")
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        filterService.delete(filterService.find(id));

        auditPublisher.publish(AuditConstants.FILTER_DELETION, id.toString());
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
