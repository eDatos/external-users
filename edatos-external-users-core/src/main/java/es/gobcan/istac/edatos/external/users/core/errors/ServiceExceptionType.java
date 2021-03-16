package es.gobcan.istac.edatos.external.users.core.errors;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType INVALID_PROC_STATUS = create("exception.external_users.invalid.proc_status");

    public static final CommonServiceExceptionType OPERATION_NOT_FOUND = create("exception.external_users.operation.not_found");
    public static final CommonServiceExceptionType OPERATION_CODE_NOT_FOUND = create("exception.external_users.operation_code.not_found");
    public static final CommonServiceExceptionType OPERATION_ALREADY_EXIST_CODE_DUPLICATED = create("exception.external_users.operation.already_exist.code_duplicated");

    public static final CommonServiceExceptionType QUERY_NOT_SUPPORTED = create("exception.external_users.query.not_supported");
}
