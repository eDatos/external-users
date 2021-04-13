package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.vo.InternationalStringVO;

public interface DataProtectionPolicyService {

    DataProtectionPolicyEntity find();
    DataProtectionPolicyEntity update(InternationalStringVO value);
}
