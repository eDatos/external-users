package es.gobcan.istac.statistical.operations.roadmap.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.DocumentoEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.FicheroEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.CustomParameterizedExceptionBuilder;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ErrorMessagesConstants;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.DocumentoRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.DocumentoService;
import es.gobcan.istac.statistical.operations.roadmap.core.service.FicheroService;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    private FicheroService ficheroService;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public DocumentoEntity save(DocumentoEntity documento, Long ficheroId) {
        FicheroEntity fichero = ficheroService.get(ficheroId);
        documento.setFichero(fichero);
        return documentoRepository.saveAndFlush(documento);
    }

    @Override
    public DocumentoEntity save(DocumentoEntity documento) {
        return documentoRepository.saveAndFlush(documento);
    }

    @Override
    public List<DocumentoEntity> findAll() {
        return documentoRepository.findAll();
    }

    @Override
    public DocumentoEntity get(Long id) {
        DocumentoEntity entity = documentoRepository.findOne(id);
        if (entity == null) {
            throw new CustomParameterizedExceptionBuilder().message(String.format(ErrorMessagesConstants.ENTITY_NOT_FOUND_MESSAGE, id)).code(ErrorConstants.ENTIDAD_NO_ENCONTRADA, String.valueOf(id))
                    .build();
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        documentoRepository.delete(id);
    }
}
