package es.gobcan.istac.edatos.external.users.core.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterizedErrorItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;

    private final String code;

    private List<String> params = new ArrayList<>();

    private List<ParameterizedErrorItem> errorItems = new ArrayList<>();

    protected ParameterizedErrorItem(String message, String code, String... params) {
        this.code = code;
        this.message = message;
        if (params != null) {
            this.params = Arrays.asList(params);
        }
    }

    public String getcode() {
        return code;
    }

    public String getMessage() {
        return message;

    }

    public List<String> getParams() {
        return params;
    }

    public List<ParameterizedErrorItem> getErrorItems() {
        return errorItems;
    }

    public void addErrorItem(ParameterizedErrorItem errorItem) {
        errorItems.add(errorItem);
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
