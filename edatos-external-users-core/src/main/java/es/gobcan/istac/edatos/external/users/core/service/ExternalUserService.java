package es.gobcan.istac.edatos.external.users.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;

public interface ExternalUserService {

    ExternalUserEntity create(ExternalUserEntity user);

    void update(String firstName, String apellido1, String apellido2, String email);

    ExternalUserEntity update(ExternalUserEntity user);

    ExternalUserEntity delete(Long id);

    ExternalUserEntity recover(Long id);

    ExternalUserEntity find(Long id);

    Page<ExternalUserEntity> find(Pageable pageable, Boolean includeDeleted, String query);
}
