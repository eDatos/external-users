package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalUserEntity;

public interface NotificationOrganismArgsService {

    String[] argsByOrganism(String organism, ExternalUserEntity externalUserEntity, String lopd);
}
