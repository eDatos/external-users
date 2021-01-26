package es.gobcan.istac.statistical.operations.roadmap.core.service;

import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.DocumentoEntity;

public interface DocumentoService {

    DocumentoEntity save(DocumentoEntity documento, Long ficheroId);

    DocumentoEntity save(DocumentoEntity documento);

    List<DocumentoEntity> findAll();

    DocumentoEntity get(Long id);

    void delete(Long id);
}
