package es.gobcan.istac.edatos.external.users.internal.rest.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.service.FilterService;
import es.gobcan.istac.edatos.external.users.internal.rest.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.internal.rest.mapper.FilterMapper;

@RestController
@RequestMapping("/api")
public class FilterResource extends AbstractResource {

    // TODO(EDATOS-3280): Modify attribute
    //private static final String ENTITY_NAME = "userManagement";

    private final FilterService filterService;

    private final FilterMapper filterMapper;

    //private final AuditEventPublisher auditPublisher;

    public FilterResource(FilterService filterService, FilterMapper filterMapper) {
        this.filterService = filterService;
        this.filterMapper = filterMapper;
    }

    @GetMapping("/filters")
    @Timed
    @PreAuthorize("@secChecker.canListAllFilters(authentication)")
    public ResponseEntity<List<FilterDto>> getAllFilters() {
        return ResponseEntity.ok(filterMapper.toDtos(filterService.findAll()));
    }
}
