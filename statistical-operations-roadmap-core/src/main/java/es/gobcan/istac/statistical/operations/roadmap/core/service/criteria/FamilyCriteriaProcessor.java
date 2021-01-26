package es.gobcan.istac.statistical.operations.roadmap.core.service.criteria;

import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.ACRONYM;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.CODE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.DESCRIPTION;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.INTERNAL_INVENTORY_DATE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.INVENTORY_DATE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.PROC_STATUS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.TITLE;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity.Properties.URN;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.InternationalStringEntity.Properties.TEXTS;
import static es.gobcan.istac.statistical.operations.roadmap.core.domain.LocalisedStringEntity.Properties.LABEL;
import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.statistical.operations.roadmap.core.domain.FamilyEntity;
import es.gobcan.istac.statistical.operations.roadmap.core.domain.enumeration.ProcStatusEnum;

public class FamilyCriteriaProcessor extends AbstractCriteriaProcessor {

    public FamilyCriteriaProcessor() {
        super(FamilyEntity.class);
    }

    public enum QueryProperty {
        // @formatter:off
        ID,
        CODE,
        TITLE,
        URN,
        ACRONYM,
        DESCRIPTION,
        INTERNAL_INVENTORY_DATE,
        PROC_STATUS,
        INVENTORY_DATE;
        // @formatter:on
    }

    @Override
    public void registerProcessors() {
        // @formatter:off

        // When the ID is specified, a search by CODE must be performed. 
        // In the internal and external API the search by ID correspond to a search by CODE.
        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.ID).sortable()
                .withEntityProperty(CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.ID)
                .withEntityProperty(CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.CODE).sortable()
                .withEntityProperty(CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.CODE)
                .withEntityProperty(CODE)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(TITLE, "t", LEFT_OUTER_JOIN)
                .withAlias("t." + TEXTS, "tl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty("tl." + LABEL)
                .build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty(TITLE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.URN).sortable()
                .withEntityProperty(URN).build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(ACRONYM, "a", LEFT_OUTER_JOIN)
                .withAlias("a." + TEXTS, "al", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.ACRONYM)
                .withEntityProperty("al." + LABEL)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(DESCRIPTION, "d", LEFT_OUTER_JOIN)
                .withAlias("d." + TEXTS, "dl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.DESCRIPTION)
                .withEntityProperty("dl." + LABEL)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.enumRestrictionProcessor(ProcStatusEnum.class)
                .withQueryProperty(QueryProperty.PROC_STATUS)
                .withEntityProperty(PROC_STATUS)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INTERNAL_INVENTORY_DATE) 
                .withEntityProperty(INTERNAL_INVENTORY_DATE)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INVENTORY_DATE) 
                .withEntityProperty(INVENTORY_DATE)
                .build());

        // @formatter:on
    }
}
