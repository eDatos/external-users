package es.gobcan.istac.edatos.external.users.core.service.criteria;

import org.springframework.stereotype.Component;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.edatos.external.users.core.domain.CategoryEntity;
import es.gobcan.istac.edatos.external.users.core.util.CriteriaUtil;

@Component
public class CategoryCriteriaProcessor extends AbstractCriteriaProcessor {

    public static final CriteriaUtil criteriaUtil = new CriteriaUtil(CategoryEntity.class);

    public static final String ENTITY_FIELD_ID = criteriaUtil.validateFieldExists("id");
    public static final String ENTITY_FIELD_CODE = criteriaUtil.validateFieldExists("code");

    public enum QueryProperty {
        ID,
        CODE,
    }

    public CategoryCriteriaProcessor() {
        super(CategoryEntity.class);
    }

    @Override
    public void registerProcessors() {
        // @formatter:off
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .longRestrictionProcessor()
                .withQueryProperty(QueryProperty.ID).sortable()
                .withEntityProperty(ENTITY_FIELD_ID)
                .build());
        registerRestrictionProcessor(RestrictionProcessorBuilder
                .stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.CODE).sortable()
                .withEntityProperty(ENTITY_FIELD_CODE)
                .build());
        // @formatter:on
    }
}
