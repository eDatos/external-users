package es.gobcan.istac.edatos.external.users.core.domain.enumeration;

import java.io.Serializable;

public enum ProcStatusEnum implements Serializable {
    DRAFT, INTERNALLY_PUBLISHED, EXTERNALLY_PUBLISHED;

    private ProcStatusEnum() {
    }

    public String getName() {
        return name();
    }
}
