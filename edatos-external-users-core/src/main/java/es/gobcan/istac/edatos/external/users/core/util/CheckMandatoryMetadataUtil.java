package es.gobcan.istac.edatos.external.users.core.util;

import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionParameters;

public class CheckMandatoryMetadataUtil {

    private CheckMandatoryMetadataUtil() {
    }

    // ----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- FAMILY VALIDATIONS ----------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    //public static void checkCreateFamily(FamilyEntity family) {
    //    checkCreateFamily(family, null);
    //}

    /**
     * Check family mandatory metadata for create it
     *
     * @param family
     */
    //public static void checkCreateFamily(FamilyEntity family, List<EDatosExceptionItem> exceptions) {
    //    if (exceptions == null) {
    //        exceptions = new ArrayList<>();
    //    }
    //
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(family.getCode(), ServiceExceptionParameters.FAMILY_CODE, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(family.getUrn(), ServiceExceptionParameters.FAMILY_URN, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(family.getTitle(), ServiceExceptionParameters.FAMILY_TITLE, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(family.getProcStatus(), ServiceExceptionParameters.FAMILY_PROC_STATUS, exceptions);
    //    StatisticalOperationsValidationUtils.checkSemanticIdentifierAsMetamacID(family.getCode(), ServiceExceptionParameters.FAMILY_CODE, exceptions);
    //
    //    if (!exceptions.isEmpty()) {
    //        throw new EDatosException(exceptions);
    //    }
    //}

    /**
     * Check family mandatory metadata for publish it internally
     *
     * @param family
     */

    //public static void checkFamilyForPublishInternally(FamilyEntity family) {
    //    checkFamilyForPublishInternally(family, null);
    //}

    //public static void checkFamilyForPublishInternally(FamilyEntity family, List<EDatosExceptionItem> exceptions) {
    //    if (exceptions == null) {
    //        exceptions = new ArrayList<>();
    //    }
    //
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(family.getInternalInventoryDate(), ServiceExceptionParameters.FAMILY_INTERNAL_INVENTORY_DATE, exceptions);
    //
    //    checkCreateFamily(family);
    //}

    /**
     * Check family mandatory metadata for publish it externally
     *
     * @param family
     */
    //public static void checkFamilyForPublishExternally(FamilyEntity family) {
    //    List<EDatosExceptionItem> exceptions = new ArrayList<>();
    //
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(family.getInventoryDate(), ServiceExceptionParameters.FAMILY_INVENTORY_DATE, exceptions);
    //
    //    checkFamilyForPublishInternally(family, exceptions);
    //}

    // -------------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- OPERATION VALIDATIONS ----------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------

    public static void checkCreateOperation(OperationEntity operation) {
        checkCreateOperation(operation, null);
    }

    /**
     * Check operation mandatory metadata for create it
     *
     * @param operation
     */
    public static void checkCreateOperation(OperationEntity operation, List<EDatosExceptionItem> exceptions) {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }

        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getCode(), ServiceExceptionParameters.OPERATION_CODE, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getUrn(), ServiceExceptionParameters.OPERATION_URN, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getTitle(), ServiceExceptionParameters.OPERATION_TITLE, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getProcStatus(), ServiceExceptionParameters.OPERATION_PROC_STATUS, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getStatus(), ServiceExceptionParameters.OPERATION_STATUS, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getSubjectArea(), ServiceExceptionParameters.OPERATION_SUBJECT_AREA, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getIndicatorSystem(), ServiceExceptionParameters.OPERATION_INDICATOR_SYSTEM, exceptions);

        //if (BooleanUtils.isNotTrue(operation.getReleaseCalendar())) {
        //    StatisticalOperationsValidationUtils.checkMetadataEmpty(operation.getReleaseCalendarAccess(), ServiceExceptionParameters.OPERATION_RELEASE_CALENDAR_ACCESS, exceptions);
        //} else {
        //    StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getReleaseCalendarAccess(), ServiceExceptionParameters.OPERATION_RELEASE_CALENDAR_ACCESS, exceptions);
        //    StatisticalOperationsValidationUtils.validateUrl(operation.getReleaseCalendarAccess(), ServiceExceptionParameters.OPERATION_RELEASE_CALENDAR_ACCESS, exceptions);
        //}

        StatisticalOperationsValidationUtils.checkSemanticIdentifierAsMetamacID(operation.getCode(), ServiceExceptionParameters.OPERATION_CODE, exceptions);

        if (!exceptions.isEmpty()) {
            throw new EDatosException(exceptions);
        }
    }

    public static void checkOperationForPublishInternally(OperationEntity operation) {
        checkOperationForPublishInternally(operation, null);
    }

    /**
     * Check operation mandatory metadata for publish it internally
     *
     * @param operationDto
     */
    public static void checkOperationForPublishInternally(OperationEntity operation, List<EDatosExceptionItem> exceptions) {

        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }

        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getCommonMetadata(), ServiceExceptionParameters.OPERATION_COMMON_METADATA, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getObjective(), ServiceExceptionParameters.OPERATION_OBJECTIVE, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getProducer(), ServiceExceptionParameters.OPERATION_PRODUCER, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getRegionalResponsible(), ServiceExceptionParameters.OPERATION_REGIONAL_RESPONSIBLE, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getInternalInventoryDate(), ServiceExceptionParameters.OPERATION_INTERNAL_INVENTORY_DATE, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getCurrentlyActive(), ServiceExceptionParameters.OPERATION_CURRENTLY_ACTIVE, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getPublisher(), ServiceExceptionParameters.OPERATION_PUBLISHER, exceptions);

        checkCreateOperation(operation, exceptions);
    }

    /**
     * Check operation mandatory metadata for publish it externally
     *
     * @param operation
     */
    public static void checkOperationForPublishExternally(OperationEntity operation) {

        List<EDatosExceptionItem> exceptions = new ArrayList<>();

        StatisticalOperationsValidationUtils.checkMetadataRequired(operation.getInventoryDate(), ServiceExceptionParameters.OPERATION_INVENTORY_DATE, exceptions);

        checkOperationForPublishInternally(operation, exceptions);
    }

    // ------------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- INSTANCE VALIDATIONS ----------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------

    /**
     * Check instance mandatory metadata for create it
     *
     * @param instance
     */
    //public static void checkCreateInstance(InstanceEntity instance, List<EDatosExceptionItem> exceptions) {
    //    if (exceptions == null) {
    //        exceptions = new ArrayList<>();
    //    }
    //
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getOrder(), ServiceExceptionParameters.INSTANCE_ORDER, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getUrn(), ServiceExceptionParameters.INSTANCE_URN, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getCode(), ServiceExceptionParameters.INSTANCE_CODE, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getTitle(), ServiceExceptionParameters.INSTANCE_TITLE, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getProcStatus(), ServiceExceptionParameters.INSTANCE_PROC_STATUS, exceptions);
    //
    //    if (instance.getBasePeriod() != null && !SdmxTimeUtils.isObservationalTimePeriod(instance.getBasePeriod())) {
    //        exceptions.add(new EDatosExceptionItem(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.INSTANCE_BASE_PERIOD));
    //    }
    //
    //    StatisticalOperationsValidationUtils.checkSemanticIdentifierAsMetamacID(instance.getCode(), ServiceExceptionParameters.INSTANCE_CODE, exceptions);
    //
    //    if (!exceptions.isEmpty()) {
    //        throw new EDatosException(exceptions);
    //    }
    //}

    /**
     * Check instance mandatory metadata for publish it internally
     *
     * @param instance
     */
    //public static void checkInstanceForPublishInternally(InstanceEntity instance) {
    //    List<EDatosExceptionItem> exceptions = new ArrayList<>();
    //
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getInstanceType(), ServiceExceptionParameters.INSTANCE_INSTANCE_TYPE, exceptions);
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getInternalInventoryDate(), ServiceExceptionParameters.INSTANCE_INTERNAL_INVENTORY_DATE, exceptions);
    //
    //    checkCreateInstance(instance, exceptions);
    //}

    /**
     * Check instance mandatory metadata for publish it externally
     *
     * @param instanceDto
     */
    //public static void checkInstanceForPublishExternally(InstanceEntity instance) {
    //    List<EDatosExceptionItem> exceptions = new ArrayList<>();
    //
    //    StatisticalOperationsValidationUtils.checkMetadataRequired(instance.getInventoryDate(), ServiceExceptionParameters.INSTANCE_INVENTORY_DATE, exceptions);
    //    checkInstanceForPublishInternally(instance);
    //}

    //public static void checkCreateInstance(InstanceEntity instance) {
    //    checkCreateInstance(instance, null);
    //}
}
