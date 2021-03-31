package es.gobcan.istac.edatos.external.users.rest.external.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;
import org.siemac.edatos.core.common.exception.EDatosException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import es.gobcan.istac.edatos.external.users.core.domain.FavoriteEntity;
import es.gobcan.istac.edatos.external.users.core.security.SecurityUtils;
import es.gobcan.istac.edatos.external.users.core.service.FavoriteService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FavoriteDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.FavoriteMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import es.gobcan.istac.edatos.external.users.rest.common.util.PaginationUtil;

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
    public ResponseEntity<FavoriteDto> getFavoriteById(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteMapper.toDto(favoriteService.find(id)));
    }

    @GetMapping
    @Timed
    public ResponseEntity<List<FavoriteDto>> getFavorites(Pageable pageable, @RequestParam(required = false) String query) {
        Page<FavoriteDto> result = favoriteService.find(query, pageable).map(favoriteMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    @PostMapping
    @Timed
    public ResponseEntity<FavoriteDto> createFavorite(@RequestBody FavoriteDto dto) throws URISyntaxException {
        if (dto != null && dto.getId() != null) {
            throw new EDatosException(CommonServiceExceptionType.PARAMETER_UNEXPECTED, "id");
        }

        if (dto != null) {
            // FIXME(EDATOS-3294):
            //  We need to validate the user creating the favorite it's doing it for himself.
            //   1. How do we know when we are dealing with an internal or an external user? SecurityUtils was
            //      meant to be used with CAS users, maybe we need to create a new class to handle external users.
            //   2. Should this be handled in the controller? The service? The validator?
            //   3. Should we assign automatically the user as the one making the request? Or should we check and
            //      if they don't match, throw a message? Like 'you cannot create favorites for other users'.
            dto.getExternalUser().setEmail(SecurityUtils.getCurrentUserLogin());
        }

        FavoriteEntity entity = favoriteMapper.toEntity(dto);
        entity = favoriteService.create(entity);
        FavoriteDto newDto = favoriteMapper.toDto(entity);

        auditPublisher.publish(AuditConstants.FILTER_CREATION, newDto.getExternalUser().getEmail());
        return ResponseEntity.created(new URI(BASE_URL + SLASH + newDto.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newDto.getId().toString())).body(newDto);
    }

    @PutMapping
    @Timed
    public ResponseEntity<FavoriteDto> updateFavorite(@RequestBody FavoriteDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new EDatosException(CommonServiceExceptionType.PARAMETER_REQUIRED, "id");
        }

        // FIXME(EDATOS-3294): same as the FIXME above
        dto.getExternalUser().setEmail(SecurityUtils.getCurrentUserLogin());

        FavoriteEntity entity = favoriteMapper.toEntity(dto);
        entity = favoriteService.update(entity);
        FavoriteDto newDto = favoriteMapper.toDto(entity);

        auditPublisher.publish(AuditConstants.FILTER_EDITION, newDto.getExternalUser().getEmail());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newDto.getId().toString())).body(newDto);
    }

    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        FavoriteEntity favorite = favoriteService.find(id);

        // FIXME(EDATOS-3294): same as the FIXME above
        if (favorite != null && favorite.getExternalUser().getEmail().equals(SecurityUtils.getCurrentUserLogin())) {
            favoriteService.delete(favorite);
        }

        auditPublisher.publish(AuditConstants.FILTER_DELETION, id.toString());
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
