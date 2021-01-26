package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.NeedStateEntity;
import es.gobcan.istac.edatos.external.users.core.repository.NeedStateRepository;
import es.gobcan.istac.edatos.external.users.core.service.NeedStateService;

@Service
public class NeedStateServiceImpl extends TableValueServiceImpl<NeedStateEntity, NeedStateRepository> implements NeedStateService {

    @Autowired
    public NeedStateServiceImpl(NeedStateRepository r) {
        super(r);
    }

}
