package es.gobcan.istac.edatos.external.users.core.errors;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType INVALID_PROC_STATUS = create("exception.external_users.invalid.proc_status");

    public static final CommonServiceExceptionType FAMILY_WITHOUT_PUBLISHED_INTERNALLY_OPERATIONS = create("exception.external_users.family.without_published_internally_operations");
    public static final CommonServiceExceptionType FAMILY_NOT_FOUND = create("exception.external_users.family.not_found");
    public static final CommonServiceExceptionType FAMILY_CODE_NOT_FOUND = create("exception.external_users.family_code.not_found");
    public static final CommonServiceExceptionType FAMILY_WITHOUT_PUBLISHED_EXTERNALLY_OPERATIONS = create("exception.external_users.family.without_published_externally_operations");
    public static final CommonServiceExceptionType FAMILY_WITHOUT_OPERATIONS = create("exception.external_users.family.without_operations");
    public static final CommonServiceExceptionType FAMILY_ALREADY_EXIST_CODE_DUPLICATED = create("exception.external_users.family.already_exist.code_duplicated");

    public static final CommonServiceExceptionType OPERATION_NOT_FOUND = create("exception.external_users.operation.not_found");
    public static final CommonServiceExceptionType OPERATION_CODE_NOT_FOUND = create("exception.external_users.operation_code.not_found");
    public static final CommonServiceExceptionType OPERATION_ALREADY_EXIST_CODE_DUPLICATED = create("exception.external_users.operation.already_exist.code_duplicated");

    public static final CommonServiceExceptionType INSTANCE_INCORRECT_OPERATION_ID = create("exception.external_users.instance.incorrect_operation_id");
    public static final CommonServiceExceptionType INSTANCE_INCORRECT_OPERATION_PROC_STATUS = create("exception.external_users.instance.incorrect_operation_proc_status");
    public static final CommonServiceExceptionType INSTANCE_NOT_FOUND = create("exception.external_users.instance.not_found");
    public static final CommonServiceExceptionType INSTANCE_CODE_NOT_FOUND = create("exception.external_users.instance_code.not_found");
    public static final CommonServiceExceptionType INSTANCE_ID_NOT_FOUND = create("exception.external_users.instance_id.not_found");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION = create("exception.external_users.instance.without_operation");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION_PUBLISHED = create("exception.external_users.instance.without_operation_published");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION_PUBLISHED_EXTERNALLY = create("exception.external_users.instance.without_operation_published_externally");
    public static final CommonServiceExceptionType INSTANCE_ALREADY_EXIST_CODE_DUPLICATED = create("exception.external_users.instance.already_exist.code_duplicated");

    public static final CommonServiceExceptionType SURVEY_TYPE_NOT_FOUND = create("exception.external_users.list.survey_type.not_found");
    public static final CommonServiceExceptionType ACTIVITY_TYPE_NOT_FOUND = create("exception.external_users.list.activity_type.not_found");
    public static final CommonServiceExceptionType COLL_METHOD_NOT_FOUND = create("exception.external_users.list.coll_method.not_found");
    public static final CommonServiceExceptionType SOURCE_DATA_NOT_FOUND = create("exception.external_users.list.sources_data.not_found");
    public static final CommonServiceExceptionType OFFICIALITY_TYPE_NOT_FOUND = create("exception.external_users.list.officiality_types.not_found");
    public static final CommonServiceExceptionType COST_NOT_FOUND = create("exception.external_users.list.cost.not_found");

    public static final CommonServiceExceptionType SECURITY_ACCESS_OPERATION_NOT_ALLOWED = create("exception.external_users.security.access_operation_not_allowed");

    public static final CommonServiceExceptionType QUERY_NOT_SUPPORTED = create("exception.external_users.query.not_supported");
    // Needs
    public static final CommonServiceExceptionType NEED_NOT_FOUND = create("exception.external_users.need.not_found");
    public static final CommonServiceExceptionType NEED_ALREADY_EXIST_CODE_DUPLICATED = create("exception.external_users.need.already_exists.code_duplicated");

}
