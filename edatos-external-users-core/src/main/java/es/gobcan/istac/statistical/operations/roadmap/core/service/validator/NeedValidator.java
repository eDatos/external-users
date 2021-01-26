package es.gobcan.istac.statistical.operations.roadmap.core.service.validator;

import java.util.ArrayList;
import java.util.List;

import org.siemac.edatos.core.common.exception.EDatosExceptionBuilder;
import org.siemac.edatos.core.common.exception.EDatosExceptionItem;
import org.siemac.edatos.core.common.exception.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionParameters;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.core.repository.NeedRepository;
import es.gobcan.istac.statistical.operations.roadmap.core.util.StatisticalOperationsValidationUtils;
import es.gobcan.istac.statistical.operations.roadmap.core.util.ValidationUtil;

@Component
public class NeedValidator extends AbstractValidator<NeedEntity> {

    @Autowired
    private NeedRepository needRepository;

    public void checkNeedCodeUnique(Long needId, String code) {
        TransactionCallback<Long> callback = new TransactionCallback<Long>() {

            @Override
            public Long doInTransaction(TransactionStatus status) {
                if (needId == null) {
                    return needRepository.countByCode(code);
                } else {
                    return needRepository.countByIdNotAndCode(needId, code);
                }
            }
        };

        TransactionTemplate template = new TransactionTemplate(platformTransactionManager);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        template.setReadOnly(true);
        Long count = template.execute(callback);

        if (count > 0) {
            throw EDatosExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.NEED_ALREADY_EXIST_CODE_DUPLICATED).withMessageParameters(code).build();
        }
    }

    public void checkNeedProcStatusForDeletion(NeedEntity need) {
        ValidationUtil.validateProcStatus(ProcStatusEnum.DRAFT, need.getProcStatus());
    }

    /***************************************************************************************************/
    public void checkCreateNeed(NeedEntity need) {
        checkCreateNeed(need, null);
    }

    public void checkCreateNeed(NeedEntity need, List<EDatosExceptionItem> exceptions) {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }

        StatisticalOperationsValidationUtils.checkMetadataRequired(need.getCode(), ServiceExceptionParameters.NEED_CODE, exceptions);
        StatisticalOperationsValidationUtils.checkMetadataRequired(need.getTitle(), ServiceExceptionParameters.NEED_TITLE, exceptions);
        StatisticalOperationsValidationUtils.checkSemanticIdentifierAsMetamacID(need.getCode(), ServiceExceptionParameters.NEED_CODE, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    /***************************************************************************************************/
    public void checkNeedForPublishInternally(NeedEntity need) {
        checkNeedForPublishInternally(need, null);
    }

    public void checkNeedForPublishInternally(NeedEntity need, List<EDatosExceptionItem> exceptions) {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }

        // TODO EDATOS-3124 Miguel validatons?

        checkCreateNeed(need, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    /***************************************************************************************************/
    public void checkNeedForPublishExternally(NeedEntity need) {
        checkNeedForPublishExternally(need, null);
    }

    public void checkNeedForPublishExternally(NeedEntity need, List<EDatosExceptionItem> exceptions) {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }

        // TODO EDATOS-3124 Miguel validatons?

        checkNeedForPublishInternally(need, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }
}
