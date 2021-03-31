package es.gobcan.istac.edatos.external.users.rest.internal.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.OperationService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.OperationDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.OperationMapper;
import es.gobcan.istac.edatos.external.users.rest.common.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(OperationResource.BASE_URL)
public class OperationResource extends AbstractResource {
    public static final String BASE_URL = "/api/operations";

    private static final String ENTITY_NAME = "operation";

    private final OperationService operationService;
    private final OperationMapper operationMapper;

    public OperationResource(OperationService operationService, OperationMapper operationMapper) {
        this.operationService = operationService;
        this.operationMapper = operationMapper;
    }

    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<OperationDto> get(@PathVariable Long id) {
        OperationEntity operation = operationService.findOperationById(id);
        OperationDto operationDto = operationMapper.toDto(operation);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationDto));
    }

    @GetMapping
    @Timed
    public ResponseEntity<List<OperationDto>> find(String query, Pageable pageable) {
        Page<OperationEntity> entities = operationService.find(query, pageable);
        Page<OperationDto> result = entities.map(operationMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, BASE_URL);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }
}
