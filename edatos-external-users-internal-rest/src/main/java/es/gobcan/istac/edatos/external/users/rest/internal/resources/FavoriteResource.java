package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.FavoriteMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;

@RestController
@RequestMapping(FavoriteResource.BASE_URL)
public class FavoriteResource extends AbstractResource {

    public static final String ENTITY_NAME = "favorite";
    public static final String BASE_URL = "/api/favorites";

    private final FavoriteService favoriteService;

    private final FavoriteMapper favoriteMapper;

    private final AuditEventPublisher auditPublisher;

    public FavoriteResource(FavoriteService favoriteService, FavoriteMapper favoriteMapper, AuditEventPublisher auditPublisher) {
        this.favoriteService = favoriteService;
        this.favoriteMapper = favoriteMapper;
        this.auditPublisher = auditPublisher;
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secChecker.canAccessFavorites(authentication)")
    public ResponseEntity<FavoriteDto> getFavoriteById(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteMapper.toDto(favoriteService.find(id)));
    }

    @GetMapping
    @Timed
    @PreAuthorize("@secChecker.canAccessFavorites(authentication)")
    public ResponseEntity<List<FavoriteDto>> getFavorites(@RequestParam(required = false) String query, Sort sort) {
        List<FavoriteDto> result = favoriteService.find(query, sort).stream().map(favoriteMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Timed
    @PreAuthorize("@secChecker.canCreateFavorites(authentication)")
    public ResponseEntity<FavoriteDto> createFavorite(@RequestBody FavoriteDto dto) throws URISyntaxException {
        if (dto != null && dto.getId() != null) {
            throw new EDatosException(CommonServiceExceptionType.PARAMETER_UNEXPECTED, "id");
        }

        FavoriteEntity entity = favoriteMapper.toEntity(dto);
        entity = favoriteService.create(entity);
        FavoriteDto newDto = favoriteMapper.toDto(entity);

        auditPublisher.publish(AuditConstants.FAVORITE_CREATION, newDto.getExternalUser().getId().toString());
        return ResponseEntity.created(new URI(BASE_URL + SLASH + newDto.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newDto.getId().toString())).body(newDto);
    }

    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("@secChecker.canDeleteFavorites(authentication)")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.delete(favoriteService.find(id));

        auditPublisher.publish(AuditConstants.FAVORITE_DELETION, id.toString());
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
