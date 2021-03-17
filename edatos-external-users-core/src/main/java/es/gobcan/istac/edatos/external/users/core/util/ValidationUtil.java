package es.gobcan.istac.edatos.external.users.core.util;

import org.siemac.edatos.core.common.exception.EDatosException;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;

import es.gobcan.istac.edatos.external.users.core.domain.OperationEntity;
import es.gobcan.istac.edatos.external.users.core.errors.ServiceExceptionType;

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
    public static void validateProcStatus(ProcStatus expectedProcStatus, ProcStatus realProcStatus) throws EDatosException {
        if (!expectedProcStatus.equals(realProcStatus)) {
            throw new EDatosException(ServiceExceptionType.INVALID_PROC_STATUS, expectedProcStatus);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------
    // ---------------------------------------------- OPERATION VALIDATIONS ----------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------

    /**
     * Check if an operation is in a correct procStatus for publish internally
     */
    public static void validateOperationProcStatusForPublishInternally(OperationEntity operation) {
        //if (!ProcStatusEnum.DRAFT.equals(operation.getProcStatus())) {
        //    throw new EDatosException(ServiceExceptionType.INVALID_PROC_STATUS, ProcStatusEnum.DRAFT);
        //}
    }
}
