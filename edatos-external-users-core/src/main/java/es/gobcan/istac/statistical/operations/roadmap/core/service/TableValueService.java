package es.gobcan.istac.statistical.operations.roadmap.core.service;

import java.util.List;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.TableValueEntity;

public interface TableValueService<E extends TableValueEntity> {

    List<E> findAll();
    E get(String code);
}
