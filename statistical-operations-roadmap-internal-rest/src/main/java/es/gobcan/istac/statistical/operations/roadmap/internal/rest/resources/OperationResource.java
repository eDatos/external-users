package es.gobcan.istac.statistical.operations.roadmap.internal.rest.resources;

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

import es.gobcan.istac.statistical.operations.roadmap.core.config.AuditConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.config.audit.AuditEventPublisher;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.service.NotificationService;
import es.gobcan.istac.statistical.operations.roadmap.core.service.OperationService;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.OperationDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.OperationMapper;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.HeaderUtil;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(OperationResource.BASE_URL)
public class OperationResource extends AbstractResource {

    // TODO EDATOS-3124 Miguel Implement the modifications of the NeedResource.java in this class

    public static final String BASE_URL = "/api/operations";

    private static final String ENTITY_NAME = "operation";

    @Autowired
    private OperationService operationService;

    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private AuditEventPublisher auditEventPublisher;

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    @Timed
    public ResponseEntity<OperationDto> create(@RequestBody OperationDto dto) throws URISyntaxException {
        if (dto.getId() != null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_EXISTE, ErrorMessagesConstants.ID_EXISTE)).body(null);
        }

        OperationEntity entity = operationMapper.toEntity(dto);
        entity = operationService.createOperation(entity);
        OperationDto result = operationMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.OPERATION_CREATION, result.getCode());
        return ResponseEntity.created(new URI(BASE_URL + SLASH + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping
    @Timed
    public ResponseEntity<OperationDto> update(@RequestBody OperationDto dto) {
        if (dto.getId() == null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_FALTA, ErrorMessagesConstants.ID_MISSING_MESSAGE)).body(null);
        }

        OperationEntity entity = operationMapper.toEntity(dto);
        entity = operationService.updateOperation(entity);
        OperationDto result = operationMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.OPERATION_EDITION, result.getCode());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString())).body(result);
    }

    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<OperationDto> get(@PathVariable Long id) {
        OperationEntity operation = operationService.findOperationById(id);
        if (operation == null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ENTIDAD_NO_ENCONTRADA, "Entity requested was not found")).build();
        }
        OperationDto operationDto = operationMapper.toDto(operation);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationDto));
    }

    @GetMapping(params = {"page", "size"})
    @Timed
    public ResponseEntity<List<OperationDto>> find(String query, Pageable pageable) {
        Page<OperationEntity> entities = operationService.find(query, pageable);
        Page<OperationDto> result = entities.map(operationMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/publish-internally")
    @Timed
    public ResponseEntity<OperationDto> publishInternally(@PathVariable Long id) {
        OperationEntity entity = operationService.publishInternallyOperation(id);
        OperationDto dto = operationMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.OPERATION_INTERNALLY_PUBLISHED, dto.getCode());
        notificationService.createNotificationForPublishInternallyOperation(entity);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString())).body(dto);
    }

    @PostMapping("/{id}/publish-externally")
    @Timed
    public ResponseEntity<OperationDto> publishExternally(@PathVariable Long id) {
        OperationEntity entity = operationService.publishExternallyOperation(id);
        OperationDto dto = operationMapper.toDto(entity);
        auditEventPublisher.publish(AuditConstants.OPERATION_EXTERNALLY_PUBLISHED, dto.getCode());
        notificationService.createNotificationForPublishExternallyOperation(entity);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dto.getId().toString())).body(dto);
    }

    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        operationService.deleteOperation(id);
        auditEventPublisher.publish(AuditConstants.OPERATION_DELETION, id.toString());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
