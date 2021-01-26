package es.gobcan.istac.edatos.external.users.core.service.criteria;

import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;

import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;
import com.arte.libs.grammar.orm.jpa.criteria.OrderProcessorBuilder;
import com.arte.libs.grammar.orm.jpa.criteria.RestrictionProcessorBuilder;

import es.gobcan.istac.edatos.external.users.core.domain.FamilyEntity;
import es.gobcan.istac.edatos.external.users.core.domain.InternationalStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.LocalisedStringEntity;
import es.gobcan.istac.edatos.external.users.core.domain.enumeration.ProcStatusEnum;

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
                .withEntityProperty(FamilyEntity.Properties.CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.ID)
                .withEntityProperty(FamilyEntity.Properties.CODE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.CODE).sortable()
                .withEntityProperty(FamilyEntity.Properties.CODE).build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.CODE)
                .withEntityProperty(FamilyEntity.Properties.CODE)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(FamilyEntity.Properties.TITLE, "t", LEFT_OUTER_JOIN)
                .withAlias("t." + InternationalStringEntity.Properties.TEXTS, "tl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty("tl." + LocalisedStringEntity.Properties.LABEL)
                .build());
        registerOrderProcessor(
                OrderProcessorBuilder.orderProcessor()
                .withQueryProperty(QueryProperty.TITLE)
                .withEntityProperty(FamilyEntity.Properties.TITLE)
                .build());

        registerRestrictionProcessor(RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withQueryProperty(QueryProperty.URN).sortable()
                .withEntityProperty(FamilyEntity.Properties.URN).build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(FamilyEntity.Properties.ACRONYM, "a", LEFT_OUTER_JOIN)
                .withAlias("a." + InternationalStringEntity.Properties.TEXTS, "al", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.ACRONYM)
                .withEntityProperty("al." + LocalisedStringEntity.Properties.LABEL)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.stringRestrictionProcessor()
                .withAlias(FamilyEntity.Properties.DESCRIPTION, "d", LEFT_OUTER_JOIN)
                .withAlias("d." + InternationalStringEntity.Properties.TEXTS, "dl", LEFT_OUTER_JOIN)
                .withQueryProperty(QueryProperty.DESCRIPTION)
                .withEntityProperty("dl." + LocalisedStringEntity.Properties.LABEL)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.enumRestrictionProcessor(ProcStatusEnum.class)
                .withQueryProperty(QueryProperty.PROC_STATUS)
                .withEntityProperty(FamilyEntity.Properties.PROC_STATUS)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INTERNAL_INVENTORY_DATE) 
                .withEntityProperty(FamilyEntity.Properties.INTERNAL_INVENTORY_DATE)
                .build());

        registerRestrictionProcessor(
                RestrictionProcessorBuilder.instantRestrictionProcessor()
                .withQueryProperty(QueryProperty.INVENTORY_DATE) 
                .withEntityProperty(FamilyEntity.Properties.INVENTORY_DATE)
                .build());

        // @formatter:on
    }
}
