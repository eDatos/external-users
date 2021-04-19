package es.gobcan.istac.edatos.external.users.core.service;

import es.gobcan.istac.edatos.external.users.core.domain.DataProtectionPolicyEntity;

public interface DataProtectionPolicyService {

    DataProtectionPolicyEntity find();
    DataProtectionPolicyEntity update(DataProtectionPolicyEntity value);
}
