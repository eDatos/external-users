package es.gobcan.istac.statistical.operations.roadmap.core.service;

import org.springframework.web.multipart.MultipartFile;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FicheroEntity;

public interface FicheroService {

    FicheroEntity create(MultipartFile multipartFile);
    FicheroEntity get(Long id);
    void delete(Long id);
    void deleteHuerfanos();
}
