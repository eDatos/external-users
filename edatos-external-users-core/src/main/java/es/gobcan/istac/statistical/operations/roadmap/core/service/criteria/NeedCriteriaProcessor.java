package es.gobcan.istac.statistical.operations.roadmap.core.service.criteria;

import static es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity.Properties.CODE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.siemac.edatos.core.common.exception.EDatosExceptionBuilder;

import com.arte.libs.grammar.domain.QueryPropertyRestriction;
import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.CriteriaProcessorContext;
import com.arte.libs.grammar.orm.jpa.criteria.converter.CriterionConverter;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.NeedEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.errors.ServiceExceptionType;
import es.gobcan.istac.statistical.operations.roadmap.core.service.criteria.util.CriteriaUtil;

public class NeedCriteriaProcessor extends AbstractCriteriaProcessor {

    public NeedCriteriaProcessor() {
        super(NeedEntity.class);
    }

    public enum QueryProperty {
        // @formatter:off
        
        CODE
        
        // @formatter:on
    }

    @Override
    public void registerProcessors() {
        // @formatter:off

        // TODO EDATOS-3124 Miguel how to search in json fields? 

        // @formatter:on
    }

    // TODO EDATOS-3124 Miguel accent insensitive search?
    public static class SqlCriterionConverter implements CriterionConverter {

        private static final String ILIKE = "ILIKE";

        @Override
        public Criterion convertToCriterion(QueryPropertyRestriction property, CriteriaProcessorContext context) {
            if (QueryProperty.CODE.name().equalsIgnoreCase(property.getLeftExpression()) && ILIKE.equalsIgnoreCase(property.getOperationType().name())) {
                List<String> fields = new ArrayList<>(Arrays.asList(CODE));
                return CriteriaUtil.buildAccentAndCaseInsensitiveCriterion(property, fields);
            }

            throw EDatosExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.QUERY_NOT_SUPPORTED).withMessageParameters(property.getLeftExpression(), property.getOperationType().name())
                    .build();
        }

    }

}
