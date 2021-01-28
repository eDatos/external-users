package es.gobcan.istac.edatos.external.users.internal.rest.dto;

import org.siemac.edatos.core.common.dto.IdentityDto;
import org.siemac.edatos.core.common.dto.InternationalStringDto;

public class SurveySourceDto extends IdentityDto implements Identifiable {

    private static final long serialVersionUID = 1L;

    private String identifier;
    private InternationalStringDto description;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public InternationalStringDto getDescription() {
        return description;
    }

    public void setDescription(InternationalStringDto description) {
        this.description = description;
    }
}
