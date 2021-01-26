package es.gobcan.istac.edatos.external.users.internal.api.v1_0.mapper;

import org.siemac.edatos.rest.search.RestPagination2Pageable;

public interface RestPagination2PageableMapper {

    public RestPagination2Pageable getFamilyCriteriaMapper();
    public RestPagination2Pageable getOperationCriteriaMapper();
    public RestPagination2Pageable getInstanceCriteriaMapper();
}
