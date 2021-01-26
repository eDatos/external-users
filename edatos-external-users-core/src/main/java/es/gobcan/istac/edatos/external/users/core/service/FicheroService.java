package es.gobcan.istac.edatos.external.users.core.service;

import org.springframework.web.multipart.MultipartFile;

import es.gobcan.istac.edatos.external.users.core.domain.FicheroEntity;

public interface FicheroService {

    FicheroEntity create(MultipartFile multipartFile);
    FicheroEntity get(Long id);
    void delete(Long id);
    void deleteHuerfanos();
}
