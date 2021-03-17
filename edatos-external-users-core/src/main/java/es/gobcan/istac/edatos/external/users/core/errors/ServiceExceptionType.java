package es.gobcan.istac.edatos.external.users.core.errors;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType INVALID_PROC_STATUS = create("exception.external_users.invalid.proc_status");

    public static final CommonServiceExceptionType OPERATION_NOT_FOUND = create("exception.external_users.operation.not_found");
    public static final CommonServiceExceptionType OPERATION_CODE_NOT_FOUND = create("exception.external_users.operation_code.not_found");
    public static final CommonServiceExceptionType OPERATION_ALREADY_EXIST_CODE_DUPLICATED = create("exception.external_users.operation.already_exist.code_duplicated");

    public static final CommonServiceExceptionType QUERY_NOT_SUPPORTED = create("exception.external_users.query.not_supported");

    public static final CommonServiceExceptionType FAVORITE_NEED_AT_LEAST_OPERATION_OR_CATEGORY = create("exception.external_users.favorite.need_at_least_operation_or_category");
    public static final CommonServiceExceptionType FAVORITE_CANNOT_SET_BOTH_OPERATION_AND_CATEGORY = create("exception.external_users.favorite.cannot_set_both_operation_and_category");
}
