package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;
import es.gobcan.istac.edatos.external.users.core.repository.DataProtectionPolicyRepository;
import es.gobcan.istac.edatos.external.users.core.service.DataProtectionPolicyService;

@Service
public class DataProtectionPolicyServiceImpl implements DataProtectionPolicyService {

    private final DataProtectionPolicyRepository dataProtectionPolicyRepository;

    public DataProtectionPolicyServiceImpl(DataProtectionPolicyRepository configurationRepository) {
        this.dataProtectionPolicyRepository = configurationRepository;
    }

    @Override
    public DataProtectionPolicyEntity find() {
        return this.dataProtectionPolicyRepository.findOne(0L);
    }

    @Override
    public DataProtectionPolicyEntity update(InternationalStringVO value) {
        DataProtectionPolicyEntity entity = find();
        entity.setValue(value);
        return dataProtectionPolicyRepository.save(entity);
    }
}
