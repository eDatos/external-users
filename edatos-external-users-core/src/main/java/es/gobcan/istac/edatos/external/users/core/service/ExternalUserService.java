package es.gobcan.istac.edatos.external.users.core.service;


import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;

import java.util.Optional;

public interface ExternalUserService {

    ExternalUserEntity create(ExternalUserEntity user);

    void update(String firstName, String apellido1, String apellido2, String email);

    ExternalUserEntity update(ExternalUserEntity user);

    ExternalUserEntity delete(String email);

    ExternalUserEntity recover(String email);

}
