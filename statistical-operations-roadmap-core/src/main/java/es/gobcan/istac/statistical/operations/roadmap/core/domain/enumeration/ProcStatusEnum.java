package es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration;

import java.io.Serializable;

public enum ProcStatusEnum implements Serializable {
    DRAFT, INTERNALLY_PUBLISHED, EXTERNALLY_PUBLISHED;

    private ProcStatusEnum() {
    }

    public String getName() {
        return name();
    }
}
