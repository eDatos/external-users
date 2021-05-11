package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;
import org.siemac.edatos.core.common.exception.EDatosException;
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

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.FilterEntity;
import es.gobcan.istac.edatos.external.users.core.service.FilterService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FilterDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.FilterMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import es.gobcan.istac.edatos.external.users.rest.common.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(FilterResource.BASE_URL)
public class FilterResource extends AbstractResource {

    public static final String ENTITY_NAME = "filter";
    public static final String BASE_URL = "/api/filters";

    private final FilterService filterService;

    private final FilterMapper filterMapper;

    private final AuditEventPublisher auditPublisher;

    private static final String PERMALINK_PATH = "https://visualizer/data.html?permalink=";

    public FilterResource(FilterService filterService, FilterMapper filterMapper, AuditEventPublisher auditPublisher) {
        this.filterService = filterService;
        this.filterMapper = filterMapper;
        this.auditPublisher = auditPublisher;
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secCheckerExternal.canAccessFilters(authentication)")
    public ResponseEntity<FilterDto> getFilterById(@PathVariable Long id) {
        FilterDto filterDto = filterMapper.toDto(filterService.find(id));
        String permalink = filterDto.getPermalink();
        filterDto.setPermalink(getPermalinkUrl(permalink));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filterDto));
    }

    private String getPermalinkUrl(String permalink) {
        return PERMALINK_PATH + permalink;
    }

    @GetMapping
    @Timed
    @PreAuthorize("@secCheckerExternal.canAccessFilters(authentication)")
    public ResponseEntity<List<FilterDto>> getFilters(Pageable pageable, @RequestParam(required = false) String query) {
        Page<FilterDto> result = filterService.findByExternalUser(query, pageable).map(filterMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @PostMapping
    @Timed
    @PreAuthorize("@secCheckerExternal.canCreateFavorites(authentication)")
    public ResponseEntity<FilterDto> createFilter(@RequestBody FilterDto dto) throws URISyntaxException {
        if (dto != null && dto.getId() != null) {
            throw new EDatosException(CommonServiceExceptionType.PARAMETER_UNEXPECTED, "id");
        }

        FilterEntity entity = filterMapper.toEntity(dto);
        entity = filterService.create(entity);
        FilterDto newDto = filterMapper.toDto(entity);

        auditPublisher.publish(AuditConstants.FILTER_CREATION, newDto.getExternalUser().getId().toString());
        return ResponseEntity.created(new URI(BASE_URL + SLASH + newDto.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newDto.getId().toString())).body(newDto);
    }

    @PutMapping
    @Timed
    @PreAuthorize("@secCheckerExternal.canUpdateFilters(authentication)")
    public ResponseEntity<FilterDto> updateFilter(@RequestBody FilterDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new EDatosException(CommonServiceExceptionType.PARAMETER_REQUIRED, "id");
        }

        FilterEntity entity = filterMapper.toEntity(dto);
        entity = filterService.update(entity);
        FilterDto newDto = filterMapper.toDto(entity);

        auditPublisher.publish(AuditConstants.FILTER_EDITION, newDto.getExternalUser().getId().toString());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newDto.getId().toString())).body(newDto);
    }

    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("@secCheckerExternal.canDeleteFilters(authentication)")
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        filterService.delete(filterService.find(id));

        auditPublisher.publish(AuditConstants.FILTER_DELETION, id.toString());
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
