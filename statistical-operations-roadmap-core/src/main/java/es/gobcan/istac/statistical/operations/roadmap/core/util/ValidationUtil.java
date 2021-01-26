package es.gobcan.istac.statistical.operations.roadmap.core.util;

import java.util.Set;

import org.siemac.edatos.core.common.exception.EDatosException;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.InstanceEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.OperationEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;

public class ValidationUtil {

    private ValidationUtil() {
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- GENERIC VALIDATIONS ----------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Check if an expectedProcStatus is equals to a realProcStatus
     * 
     * @param expectedProcStatus
     * @param realProcStatus
     */
    public static void validateProcStatus(ProcStatusEnum expectedProcStatus, ProcStatusEnum realProcStatus) throws EDatosException {
        if (!expectedProcStatus.equals(realProcStatus)) {
            throw new EDatosException(ServiceExceptionType.INVALID_PROC_STATUS, expectedProcStatus);
        }
    }

    // ----------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- FAMILY VALIDATIONS ----------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

    /**
     * Check if a family is related with some operations
     * 
     * @param familyDto
     * @param operationsForFamily
     */
    public static void validateIfFamilyRelatedWithOperations(Set<OperationEntity> operationsForFamily) {
        if (operationsForFamily.isEmpty()) {
            throw new EDatosException(ServiceExceptionType.FAMILY_WITHOUT_OPERATIONS);
        }
    }

    /**
     * Check if a family is related with any operation with INTERNALLY_PUBLISHED or EXTERNALLY_PUBLISHED ProcStatus
     *
     * @param operations
     */
    public static void validateOperationsForPublishFamilyInternally(Set<OperationEntity> operations) {

        validateIfFamilyRelatedWithOperations(operations);

        for (OperationEntity operation : operations) {
            if (!ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
                return;
            }
        }

        throw new EDatosException(ServiceExceptionType.FAMILY_WITHOUT_PUBLISHED_INTERNALLY_OPERATIONS);
    }

    /**
     * Check if a family is related with any operation with EXTERNALLY_PUBLISHED ProcStatus
     *
     * @param operations
     */
    public static void validateOperationsForPublishFamilyExternally(Set<OperationEntity> operations) {
        for (OperationEntity operation : operations) {
            if (ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(operation.getProcStatus())) {
                return;
            }
        }

        throw new EDatosException(ServiceExceptionType.FAMILY_WITHOUT_PUBLISHED_EXTERNALLY_OPERATIONS);
    }

    /**
     * Check if a family is in a correct procStatus for publish internally
     * 
     * @param familyDto
     */
    public static void validateFamilyProcStatusForPublishInternally(FamilyEntity family) {
        if (!ProcStatusEnum.DRAFT.equals(family.getProcStatus())) {
            throw new EDatosException(ServiceExceptionType.INVALID_PROC_STATUS, ProcStatusEnum.DRAFT);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- OPERATION VALIDATIONS ----------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------

    /**
     * Check if an operation is in a correct procStatus for publish internally
     *
     * @param operation
     */
    public static void validateOperationProcStatusForPublishInternally(OperationEntity operation) {
        if (!ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
            throw new EDatosException(ServiceExceptionType.INVALID_PROC_STATUS, ProcStatusEnum.DRAFT);
        }
    }

    // ------------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- INSTANCE VALIDATIONS ----------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------

    /**
     * Check if an instance is in a correct procStatus for publish internally
     *
     * @param instance
     */
    public static void validateInstanceProcStatusForPublishInternally(InstanceEntity instance) {
        if (!ProcStatusEnum.DRAFT.equals(instance.getProcStatus())) {
            throw new EDatosException(ServiceExceptionType.INVALID_PROC_STATUS, ProcStatusEnum.DRAFT);
        }
    }

    /**
     * Check if the operation id is the same that was persisted
     * 
     * @param operationIdPersisted
     * @param operationIdForPersist
     */
    public static void validateOperationForInstance(Long operationIdPersisted, Long operationIdForPersist) {
        if (operationIdForPersist.compareTo(operationIdPersisted) != 0) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_INCORRECT_OPERATION_ID);
        }

    }

    /**
     * Check if the operation proc_status isn't DRAFT
     *
     * @param operation
     */
    public static void validateOperationProcStatusForSaveInstance(OperationEntity operation) {
        if (ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_INCORRECT_OPERATION_PROC_STATUS);
        }
    }

    /**
     * Check if the instance is related at least with one published internally or externally operation
     *
     * @param operation
     */
    public static void validateOperationForPublishInstanceInternally(OperationEntity operation) {
        if (operation == null) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION);
        }

        if (ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION_PUBLISHED);
        }
    }

    /**
     * Check if the instance is related at least with one published externally operation
     *
     * @param operation
     */
    public static void validateOperationForPublishInstanceExternally(OperationEntity operation) {
        if (operation == null) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION);
        }

        if (!ProcStatusEnum.EXTERNALLY_PUBLISHED.equals(operation.getProcStatus())) {
            throw new EDatosException(ServiceExceptionType.INSTANCE_WITHOUT_OPERATION_PUBLISHED_EXTERNALLY);
        }

    }

    /**
     * Check if the expectedSize and the realSize of an instancesList is correct
     *
     * @param expectedSize
     * @param realSize
     */
    public static void checkUpdateInstancesOrder(int expectedSize, int realSize) {
        if (expectedSize != realSize) {
            throw new EDatosException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.INSTANCES_ID_LIST_SIZE);
        }
    }
}
