package es.gobcan.istac.statistical.operations.roadmap.internal.rest.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.DocumentoEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.service.DocumentoService;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto.DocumentoDto;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.mapper.DocumentoMapper;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.ControllerUtil;
import es.gobcan.istac.statistical.operations.roadmap.internal.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping(DocumentoResource.BASE_URL)
public class DocumentoResource extends AbstractResource {

    public static final String BASE_URL = "/api/documentos";
    private static final String ENTITY_NAME = "documento";

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private DocumentoMapper documentoMapper;

    @PostMapping
    @Timed
    @PreAuthorize("@secChecker.puedeCrearDocumento(authentication)")
    public ResponseEntity<DocumentoDto> create(@RequestBody DocumentoDto documentoDto, Long ficheroId) throws URISyntaxException {
        DocumentoEntity documento = documentoMapper.toEntity(documentoDto);
        DocumentoEntity result = documentoService.save(documento, ficheroId);
        DocumentoDto resultDto = documentoMapper.toDto(result);
        return ResponseEntity.created(new URI(BASE_URL + SLASH + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(resultDto);
    }

    @PutMapping
    @Timed
    @PreAuthorize("@secChecker.puedeModificarDocumento(authentication)")
    public ResponseEntity<DocumentoDto> update(@Valid @RequestBody DocumentoDto documentoDto) {
        if (documentoDto.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ID_FALTA, "An id is required")).body(null);
        }
        DocumentoEntity documento = documentoMapper.toEntity(documentoDto);
        documento = documentoService.save(documento);
        DocumentoDto result = documentoMapper.toDto(documento);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentoDto.getId().toString())).body(result);
    }

    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("@secChecker.puedeConsultarDocumento(authentication)")
    public ResponseEntity<DocumentoDto> get(@PathVariable Long id) {
        DocumentoEntity documento = documentoService.get(id);
        if (documento == null) {
            return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ErrorConstants.ENTIDAD_NO_ENCONTRADA, "Entity requested was not found")).build();
        }
        DocumentoDto documentoDto = documentoMapper.toDto(documento);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentoDto));
    }

    @GetMapping(value = "/{id}/download", consumes = "*/*", produces = "*/*")
    @Timed
    @PreAuthorize("@secChecker.puedeConsultarDocumento(authentication)")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        DocumentoEntity documento = documentoService.get(id);
        if (documento == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            ControllerUtil.download(documento.getFichero(), response);
        }
    }

    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("@secChecker.puedeBorrarDocumento(authentication)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
