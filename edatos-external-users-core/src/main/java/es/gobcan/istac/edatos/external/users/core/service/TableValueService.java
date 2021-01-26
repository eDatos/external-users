package es.gobcan.istac.edatos.external.users.core.service;

import java.util.List;

import es.gobcan.istac.edatos.external.users.core.domain.TableValueEntity;

public interface TableValueService<E extends TableValueEntity> {

    List<E> findAll();
    E get(String code);
}
