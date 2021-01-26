package es.gobcan.istac.edatos.external.users.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.edatos.external.users.core.domain.TableValueEntity;
import es.gobcan.istac.edatos.external.users.core.service.TableValueService;
import es.gobcan.istac.edatos.external.users.core.repository.TableValueRepository;

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
