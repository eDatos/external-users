package es.gobcan.istac.statistical.operations.roadmap.internal.rest.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.service.NeedService;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.NeedBaseDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.NeedDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.NeedMapper;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.HeaderUtil;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(NeedResource.BASE_URL)
public class NeedResource extends AbstractResource {

    // TODO EDATOS-3124 Miguel Añadir, a cada uno de los métodos, los @PreAuthorize correspondientes

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String BASE_URL = "/api/needs";

    private static final String ENTITY_NAME = "need";

    private NeedService needService;

    private NeedMapper needMapper;

    private AuditEventPublisher auditEventPublisher;

    public NeedResource(NeedService needService, NeedMapper needMapper, AuditEventPublisher auditEventPublisher) {
        this.needService = needService;
        this.needMapper = needMapper;
        this.auditEventPublisher = auditEventPublisher;
    }

    @PostMapping
    @Timed
    public ResponseEntity<NeedDto> create(@RequestBody NeedDto needDto) throws URISyntaxException {
        if (needDto.getId() != null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_EXISTE, ErrorMessagesConstants.ID_EXISTE)).body(null);
        }

        NeedEntity needEntity = needMapper.toEntity(needDto);
        needEntity = needService.create(needEntity);
        NeedDto result = needMapper.toDto(needEntity);
        auditEventPublisher.publish(AuditConstants.NEED_CREATION, result.getCode());
        return ResponseEntity.created(new URI(BASE_URL + SLASH + result.getId())).header(ENTITY_NAME, result.getId().toString()).body(result);
    }

    @PutMapping
    @Timed
    public ResponseEntity<NeedDto> update(@RequestBody NeedDto needDto) {
        if (needDto.getId() == null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_FALTA, ErrorMessagesConstants.ID_MISSING_MESSAGE)).body(null);
        }

        NeedEntity needEntity = needMapper.toEntity(needDto);
        needEntity = needService.update(needEntity);
        NeedDto result = needMapper.toDto(needEntity);
        auditEventPublisher.publish(AuditConstants.NEED_EDITION, result.getCode());
        return ResponseEntity.ok().header(ENTITY_NAME, result.getId().toString()).body(result);

    }

    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<NeedDto> get(@PathVariable Long id) {
        NeedEntity needEntity = needService.findById(id);
        NeedDto needDto = needMapper.toDto(needEntity);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(needDto));
    }

    @GetMapping
    @Timed
    public ResponseEntity<List<NeedBaseDto>> find(String query, Pageable pageable) {
        Page<NeedBaseDto> result = needService.find(query, pageable).map(needMapper::toBaseDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        NeedEntity needEntity = needService.findById(id);
        if (needEntity == null) {
            // TODO EDATOS-3124 Miguel I10n this message? message.properties instead constant
            return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ENTIDAD_NO_ENCONTRADA, "Entity requested was not found")).build();
        }
        needService.delete(needEntity);
        auditEventPublisher.publish(AuditConstants.NEED_DELETION, needEntity.getCode());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();

    }
}
