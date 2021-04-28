package es.gobcan.istac.edatos.external.users.core.errors;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType INVALID_PROC_STATUS = create("exception.external_users.invalid.proc_status");

    public static final CommonServiceExceptionType GENERIC_ERROR = create("exception.external_users.generic");

    /********
     * ITEM_RESOURCE
     ********/
    public static final CommonServiceExceptionType ITEM_RESOURCE_PARENT_NOT_FOUND = create("exception.external_users.item_resource.parent_not_found");

    /********
     * OPERATION
     ********/
    public static final CommonServiceExceptionType OPERATION_NOT_FOUND = create("exception.external_users.operation.not_found");
    public static final CommonServiceExceptionType OPERATION_CODE_NOT_FOUND = create("exception.external_users.operation_code.not_found");
    public static final CommonServiceExceptionType OPERATION_ALREADY_EXIST_CODE_DUPLICATED = create("exception.external_users.operation.already_exist.code_duplicated");

    /********
     * QUERY
     ********/
    public static final CommonServiceExceptionType QUERY_NOT_SUPPORTED = create("exception.external_users.query.not_supported");

    /********
     * LOGIN
     ********/
    public static final CommonServiceExceptionType LOGIN_USER_DISABLED = create("exception.external_users.login.user_disabled");
    public static final CommonServiceExceptionType LOGIN_VOID = create("exception.external_users.login.void");
    public static final CommonServiceExceptionType LOGIN_FAILED = create("exception.external_users.login.failed");

    /********
     * EXTERNAL_USER
     ********/
    public static final CommonServiceExceptionType EXTERNAL_USER_CREATE = create("exception.external_users.create");
    public static final CommonServiceExceptionType EXTERNAL_USER_DELETED = create("exception.external_users.deleted");
    public static final CommonServiceExceptionType EXTERNAL_USER_DEACTIVATE = create("exception.external_users.deactivate");

    /********
     * PASSWORD
     ********/
    public static final CommonServiceExceptionType PASSWORD_NOT_MATCH = create("exception.external_users.password.not_match");

}
