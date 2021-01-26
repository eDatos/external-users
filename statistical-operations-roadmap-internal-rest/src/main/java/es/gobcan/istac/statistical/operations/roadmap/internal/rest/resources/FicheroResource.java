package es.gobcan.istac.statistical.operations.roadmap.internal.rest.resources;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FicheroEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.service.FicheroService;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.FicheroDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.FicheroMapper;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.HeaderUtil;

@RestController
@RequestMapping(FicheroResource.BASE_URL)
public class FicheroResource extends AbstractResource {

    public static final String BASE_URL = "/api/ficheros";
    private static final String ENTITY_NAME = "fichero";

    @Autowired
    private FicheroService ficheroService;

    @Autowired
    private FicheroMapper ficheroMapper;

    @PostMapping
    @Timed
    @PreAuthorize("@secChecker.puedeCrearDocumento(authentication)")
    public ResponseEntity<FicheroDto> create(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.FICHERO_VACIO, "El fichero subido está vacío")).body(null);
        }

        FicheroEntity fichero = ficheroService.create(file);
        FicheroDto result = ficheroMapper.toDto(fichero);
        return ResponseEntity.created(new URI(BASE_URL + SLASH + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }
}
