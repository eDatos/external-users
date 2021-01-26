package es.gobcan.istac.statistical.operations.roadmap.internal.rest.resources;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.statistical.operations.roadmap.core.service.NeedTypeService;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.NeedTypeDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.NeedTypeMapper;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(NeedTypeResource.BASE_URL)
public class NeedTypeResource extends AbstractResource {

    // TODO EDATOS-3124 Miguel Añadir, a cada uno de los métodos, los @PreAuthorize correspondientes

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String BASE_URL = "/api/needTypes";

    private NeedTypeService needTypeService;

    private NeedTypeMapper needTypeMapper;

    public NeedTypeResource(NeedTypeService needTypeService, NeedTypeMapper needTypeMapper) {
        this.needTypeService = needTypeService;
        this.needTypeMapper = needTypeMapper;
    }

    @GetMapping
    @Timed
    public List<NeedTypeDto> findAll() {
        return needTypeMapper.toDtos(needTypeService.findAll());
    }

    @GetMapping("/{code}")
    @Timed
    public ResponseEntity<NeedTypeDto> get(@PathVariable String code) {
        NeedTypeDto needTypeDto = needTypeMapper.toDto(needTypeService.get(code));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(needTypeDto));
    }
}
