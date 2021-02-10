package es.gobcan.istac.edatos.external.users.rest.common.dto;

import java.io.Serializable;

import org.siemac.edatos.core.common.dto.InternationalStringDto;

public abstract class TableValueDto implements Serializable, Identifiable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private InternationalStringDto title;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InternationalStringDto getTitle() {
        return title;
    }

    public void setTitle(InternationalStringDto title) {
        this.title = title;
    }

}
