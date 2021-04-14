package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;

import java.util.Optional;

public interface LoginService {

    public ExternalUserEntity authenticate(String login, String password);
}
