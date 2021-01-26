package es.gobcan.istac.statistical.operations.roadmap.internal.rest.dto;

import java.time.Instant;

public class NeedDto extends NeedBaseDto {

    private static final long serialVersionUID = 1L;

    private NeedTypeDto       type;
    private NeedStateDto      state;
    private Instant           identificationDate;

    public NeedTypeDto getType() {
        return type;
    }

    public void setType(NeedTypeDto needTypeDto) {
        this.type = needTypeDto;
    }

    public NeedStateDto getState() {
        return state;
    }

    public void setState(NeedStateDto state) {
        this.state = state;
    }

    public Instant getIdentificationDate() {
        return identificationDate;
    }

    public void setIdentificationDate(Instant identificationDate) {
        this.identificationDate = identificationDate;
    }

    @Override
    public String toString() {
        return "NeedDto (id = " + getId() + ", code = " + getCode() + ", title = " + getTitle() + ")";
    }
}
