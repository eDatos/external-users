package es.gobcan.istac.statistical.operations.roadmap.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedTypeEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.NeedTypeRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.NeedTypeService;

@Service
public class NeedTypeServiceImpl extends TableValueServiceImpl<NeedTypeEntity, NeedTypeRepository> implements NeedTypeService {

    @Autowired
    public NeedTypeServiceImpl(NeedTypeRepository r) {
        super(r);
    }

}
