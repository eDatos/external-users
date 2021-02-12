package es.gobcan.istac.edatos.external.users.rest.common.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.config.AuditConstants;
import es.gobcan.istac.edatos.external.users.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.edatos.external.users.core.domain.FamilyEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorConstants;
import es.gobcan.istac.edatos.external.users.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.edatos.external.users.core.service.FamilyService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.FamilyDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.FamilyMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.HeaderUtil;
import es.gobcan.istac.edatos.external.users.rest.common.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(FamilyResource.BASE_URL)
public class FamilyResource extends AbstractResource {

    // TODO EDATOS-3130 Añadir, a cada uno de los métodos, los @PreAuthorize correspondientes
    // TODO EDATOS-3124 Miguel Implement the modifications of the NeedResource.java in this class

    public static final String BASE_URL = "/api/families";
    private static final String ENTITY_NAME = "family";

    @Autowired
    private FamilyService familyService;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private AuditEventPublisher auditEventPublisher;

    @PostMapping
    @Timed
    public ResponseEntity<FamilyDto> create(@RequestBody FamilyDto dto) throws URISyntaxException {
        if (dto.getId() != null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_EXISTE, ErrorMessagesConstants.ID_EXISTE)).body(null);
        }

        FamilyEntity entity = familyMapper.toEntity(dto);
        entity = familyService.create(entity);
        FamilyDto result = familyMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.FAMILY_CREATION, result.getCode());
        return ResponseEntity.created(new URI(BASE_URL + SLASH + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping
    @Timed
    public ResponseEntity<FamilyDto> update(@RequestBody FamilyDto dto) {
        if (dto.getId() == null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_FALTA, ErrorMessagesConstants.ID_MISSING_MESSAGE)).body(null);
        }

        FamilyEntity entity = familyMapper.toEntity(dto);
        entity = familyService.updateFamily(entity);
        FamilyDto result = familyMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.FAMILY_EDITION, result.getCode());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString())).body(result);
    }

    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<FamilyDto> get(@PathVariable Long id) {
        FamilyEntity family = familyService.findFamilyById(id);
        if (family == null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ENTIDAD_NO_ENCONTRADA, "Entity requested was not found")).build();
        }
        FamilyDto familyDto = familyMapper.toDto(family);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(familyDto));
    }

    @GetMapping(params = {"page", "size"})
    @Timed
    public ResponseEntity<List<FamilyDto>> find(String query, Pageable pageable) {
        Page<FamilyEntity> entities = familyService.find(query, pageable);
        Page<FamilyDto> result = entities.map(familyMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/publish-internally")
    @Timed
    public ResponseEntity<FamilyDto> publishInternally(@PathVariable Long id) {
        FamilyEntity entity = familyService.publishInternallyFamily(id);
        FamilyDto dto = familyMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.FAMILY_INTERNALLY_PUBLISHED, dto.getCode());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString())).body(dto);
    }

    @PostMapping("/{id}/publish-externally")
    @Timed
    public ResponseEntity<FamilyDto> publishExternally(@PathVariable Long id) {
        FamilyEntity entity = familyService.publishExternallyFamily(id);
        FamilyDto dto = familyMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.FAMILY_EXTERNALLY_PUBLISHED, dto.getCode());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString())).body(dto);
    }

    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        familyService.deleteFamily(id);
        auditEventPublisher.publish(AuditConstants.FAMILY_DELETION, id.toString());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
