package es.gobcan.istac.statistical.operations.roadmap.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.TableValueEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.TableValueRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.TableValueService;

public abstract class TableValueServiceImpl<E extends TableValueEntity, R extends TableValueRepository<E>> implements TableValueService<E> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private R              r;

    public TableValueServiceImpl(R r) {
        this.r = r;
    }

    @Override
    public List<E> findAll() {
        return r.findAll();
    }

    @Override
    public E get(String code) {
        return r.findByCode(code);
    }

}
