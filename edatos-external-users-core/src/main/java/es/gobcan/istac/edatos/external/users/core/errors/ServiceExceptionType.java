package es.gobcan.istac.edatos.external.users.core.errors;

import org.siemac.edatos.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType INVALID_PROC_STATUS = create("exception.extusers.invalid.proc_status");

    public static final CommonServiceExceptionType FAMILY_WITHOUT_PUBLISHED_INTERNALLY_OPERATIONS = create("exception.extusers.family.without_published_internally_operations");
    public static final CommonServiceExceptionType FAMILY_NOT_FOUND = create("exception.extusers.family.not_found");
    public static final CommonServiceExceptionType FAMILY_CODE_NOT_FOUND = create("exception.extusers.family_code.not_found");
    public static final CommonServiceExceptionType FAMILY_WITHOUT_PUBLISHED_EXTERNALLY_OPERATIONS = create("exception.extusers.family.without_published_externally_operations");
    public static final CommonServiceExceptionType FAMILY_WITHOUT_OPERATIONS = create("exception.extusers.family.without_operations");
    public static final CommonServiceExceptionType FAMILY_ALREADY_EXIST_CODE_DUPLICATED = create("exception.extusers.family.already_exist.code_duplicated");

    public static final CommonServiceExceptionType OPERATION_NOT_FOUND = create("exception.extusers.operation.not_found");
    public static final CommonServiceExceptionType OPERATION_CODE_NOT_FOUND = create("exception.extusers.operation_code.not_found");
    public static final CommonServiceExceptionType OPERATION_ALREADY_EXIST_CODE_DUPLICATED = create("exception.extusers.operation.already_exist.code_duplicated");

    public static final CommonServiceExceptionType INSTANCE_INCORRECT_OPERATION_ID = create("exception.extusers.instance.incorrect_operation_id");
    public static final CommonServiceExceptionType INSTANCE_INCORRECT_OPERATION_PROC_STATUS = create("exception.extusers.instance.incorrect_operation_proc_status");
    public static final CommonServiceExceptionType INSTANCE_NOT_FOUND = create("exception.extusers.instance.not_found");
    public static final CommonServiceExceptionType INSTANCE_CODE_NOT_FOUND = create("exception.extusers.instance_code.not_found");
    public static final CommonServiceExceptionType INSTANCE_ID_NOT_FOUND = create("exception.extusers.instance_id.not_found");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION = create("exception.extusers.instance.without_operation");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION_PUBLISHED = create("exception.extusers.instance.without_operation_published");
    public static final CommonServiceExceptionType INSTANCE_WITHOUT_OPERATION_PUBLISHED_EXTERNALLY = create("exception.extusers.instance.without_operation_published_externally");
    public static final CommonServiceExceptionType INSTANCE_ALREADY_EXIST_CODE_DUPLICATED = create("exception.extusers.instance.already_exist.code_duplicated");

    public static final CommonServiceExceptionType SURVEY_TYPE_NOT_FOUND = create("exception.extusers.list.survey_type.not_found");
    public static final CommonServiceExceptionType ACTIVITY_TYPE_NOT_FOUND = create("exception.extusers.list.activity_type.not_found");
    public static final CommonServiceExceptionType COLL_METHOD_NOT_FOUND = create("exception.extusers.list.coll_method.not_found");
    public static final CommonServiceExceptionType SOURCE_DATA_NOT_FOUND = create("exception.extusers.list.sources_data.not_found");
    public static final CommonServiceExceptionType OFFICIALITY_TYPE_NOT_FOUND = create("exception.extusers.list.officiality_types.not_found");
    public static final CommonServiceExceptionType COST_NOT_FOUND = create("exception.extusers.list.cost.not_found");

    public static final CommonServiceExceptionType SECURITY_ACCESS_OPERATION_NOT_ALLOWED = create("exception.extusers.security.access_operation_not_allowed");

    public static final CommonServiceExceptionType QUERY_NOT_SUPPORTED = create("exception.extusers.query.not_supported");
    // Needs
    public static final CommonServiceExceptionType NEED_NOT_FOUND = create("exception.extusers.need.not_found");
    public static final CommonServiceExceptionType NEED_ALREADY_EXIST_CODE_DUPLICATED = create("exception.extusers.need.already_exists.code_duplicated");

}
