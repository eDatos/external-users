package es.gobcan.istac.edatos.external.users.internal.api.v1_0.mapper;

import javax.ws.rs.core.Response.Status;

import org.siemac.edatos.rest.common.query.domain.EDatosRestOrder;
import org.siemac.edatos.rest.exception.RestException;
import org.siemac.edatos.rest.exception.util.RestExceptionUtils;
import org.siemac.edatos.rest.search.RestPagination2Pageable;
import org.siemac.edatos.rest.search.RestPagination2Pageable.CriteriaCallback;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.FamilyCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InstanceCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OperationCriteriaPropertyOrder;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.FamilyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InstanceEntity;
import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.internal.api.exception.RestServiceExceptionType;

@Component
public class RestPagination2PageableMapperImpl implements RestPagination2PageableMapper {

    private RestPagination2Pageable familyPaginationMapper = null;
    private RestPagination2Pageable operationPaginationMapper = null;
    private RestPagination2Pageable instancePaginationMapper = null;

    public RestPagination2PageableMapperImpl() {
        familyPaginationMapper = new RestPagination2Pageable(FamilyCriteriaPropertyOrder.class, new FamilyCriteriaCallback());
        operationPaginationMapper = new RestPagination2Pageable(OperationCriteriaPropertyOrder.class, new OperationCriteriaCallback());
        instancePaginationMapper = new RestPagination2Pageable(InstanceCriteriaPropertyOrder.class, new InstanceCriteriaCallback());
    }

    @Override
    public RestPagination2Pageable getFamilyCriteriaMapper() {
        return familyPaginationMapper;
    }

    @Override
    public RestPagination2Pageable getOperationCriteriaMapper() {
        return operationPaginationMapper;
    }

    @Override
    public RestPagination2Pageable getInstanceCriteriaMapper() {
        return instancePaginationMapper;
    }

    private class FamilyCriteriaCallback implements CriteriaCallback {

        @Override
        public String retrievePropertyOrder(EDatosRestOrder order) {
            FamilyCriteriaPropertyOrder propertyOrder = FamilyCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyOrder) {
                case ID:
                    return FamilyEntity.Properties.CODE;
                default:
                    throw toRestExceptionParameterIncorrect(propertyOrder.name());
            }
        }

        @Override
        public String retrievePropertyOrderDefault() {
            return FamilyEntity.Properties.CODE;
        }
    }

    private class OperationCriteriaCallback implements CriteriaCallback {

        @Override
        public String retrievePropertyOrder(EDatosRestOrder order) {
            OperationCriteriaPropertyOrder propertyOrder = OperationCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyOrder) {
                case ID:
                    return OperationEntity.Properties.CODE;
                default:
                    throw toRestExceptionParameterIncorrect(propertyOrder.name());
            }
        }

        @Override
        public String retrievePropertyOrderDefault() {
            return OperationEntity.Properties.CODE;
        }
    }

    private class InstanceCriteriaCallback implements CriteriaCallback {

        @Override
        public String retrievePropertyOrder(EDatosRestOrder order) {
            InstanceCriteriaPropertyOrder propertyOrder = InstanceCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyOrder) {
                case ID:
                    return InstanceEntity.Properties.CODE;
                default:
                    throw toRestExceptionParameterIncorrect(propertyOrder.name());
            }
        }

        @Override
        public String retrievePropertyOrderDefault() {
            return InstanceEntity.Properties.CODE;
        }
    }

    private RestException toRestExceptionParameterIncorrect(String parameter) {
        org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.PARAMETER_INCORRECT, parameter);
        return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
    }
}
