package es.gobcan.istac.edatos.external.users.rest.common.resources;

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

import es.gobcan.istac.edatos.external.users.core.service.NeedStateService;
import es.gobcan.istac.edatos.external.users.rest.common.dto.NeedStateDto;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.NeedStateMapper;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(NeedStateResource.BASE_URL)
public class NeedStateResource extends AbstractResource {

    // TODO EDATOS-3124 Miguel Añadir, a cada uno de los métodos, los @PreAuthorize correspondientes

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String BASE_URL = "/api/needStates";

    private NeedStateService needStateService;

    private NeedStateMapper needStateMapper;

    public NeedStateResource(NeedStateService needStateService, NeedStateMapper needStateMapper) {
        this.needStateService = needStateService;
        this.needStateMapper = needStateMapper;
    }

    @GetMapping
    @Timed
    public List<NeedStateDto> findAll() {
        return needStateMapper.toDtos(needStateService.findAll());
    }

    @GetMapping("/{code}")
    @Timed
    public ResponseEntity<NeedStateDto> get(@PathVariable String code) {
        NeedStateDto needStateDto = needStateMapper.toDto(needStateService.get(code));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(needStateDto));
    }

}
