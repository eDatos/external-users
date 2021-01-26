package es.gobcan.istac.statistical.operations.roadmap.internal.api.v1_0.service.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.UriTemplate;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;

public class InternalWebApplicationNavigation {

    private static final String ANCHOR = "#";
    private static final String SEPARATOR = "/";
    private static final String OPERATION_ID_PARAMETER = "operationIdParam";
    private static final String RESOURCE_ID_PARAMETER = "resourceIdParam";

    private final UriTemplate familyTemplate;
    private final UriTemplate operationTemplate;
    private final UriTemplate instanceTemplate;

    public InternalWebApplicationNavigation(String webApplicationPath) {
        this.operationTemplate = new UriTemplate(webApplicationPath + SEPARATOR + ANCHOR + NameTokens.OPERATION_LIST_PAGE + SEPARATOR + NameTokens.OPERATION_PAGE + ";"
                + PlaceRequestParams.OPERATION_PARAM + "=" + "{" + RESOURCE_ID_PARAMETER + "}");
        this.instanceTemplate = new UriTemplate(
                webApplicationPath + SEPARATOR + ANCHOR + NameTokens.OPERATION_LIST_PAGE + SEPARATOR + NameTokens.OPERATION_PAGE + ";" + PlaceRequestParams.OPERATION_PARAM + "=" + "{"
                        + OPERATION_ID_PARAMETER + "}/" + NameTokens.INSTANCE_PAGE + ";" + PlaceRequestParams.INSTANCE_PARAM + "={" + RESOURCE_ID_PARAMETER + "}");
        this.familyTemplate = new UriTemplate(webApplicationPath + SEPARATOR + ANCHOR + NameTokens.FAMILY_LIST_PAGE + SEPARATOR + NameTokens.FAMILY_PAGE + ";" + PlaceRequestParams.FAMILY_PARAM + "="
                + "{" + RESOURCE_ID_PARAMETER + "}");
    }

    public String buildOperationUrl(String operationCode) {
        Map<String, String> parameters = new HashMap<>(1);
        parameters.put(RESOURCE_ID_PARAMETER, operationCode);
        return this.operationTemplate.expand(parameters).toString();
    }

    public String buildOperationUrl(OperationEntity operation) {
        return this.buildOperationUrl(operation.getCode());
    }

    public String buildInstanceUrl(InstanceEntity instance) {
        Map<String, String> parameters = new HashMap<>(2);
        parameters.put(OPERATION_ID_PARAMETER, instance.getOperation().getCode());
        parameters.put(RESOURCE_ID_PARAMETER, instance.getCode());
        return this.instanceTemplate.expand(parameters).toString();
    }

    public String buildFamilyUrl(FamilyEntity family) {
        Map<String, String> parameters = new HashMap<>(1);
        parameters.put(RESOURCE_ID_PARAMETER, family.getCode());
        return this.familyTemplate.expand(parameters).toString();
    }
}
