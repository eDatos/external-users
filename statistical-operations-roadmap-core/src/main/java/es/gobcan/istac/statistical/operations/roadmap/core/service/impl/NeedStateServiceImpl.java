package es.gobcan.istac.statistical.operations.roadmap.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedStateEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.NeedStateRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.service.NeedStateService;

@Service
public class NeedStateServiceImpl extends TableValueServiceImpl<NeedStateEntity, NeedStateRepository> implements NeedStateService {

    @Autowired
    public NeedStateServiceImpl(NeedStateRepository r) {
        super(r);
    }

}
